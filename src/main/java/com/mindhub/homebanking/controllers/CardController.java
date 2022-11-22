package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.CardService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Service.TransactionService;
import com.mindhub.homebanking.dtos.CardTransactionDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    CardService cardService;
    @Autowired
    ClientService clientService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;



    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCards(Authentication authentication, @RequestParam CardType cardType, @RequestParam ColorType colorType) {
        Client clientCurrent = clientService.findByEmail(authentication.getName());
        Set<Card> cards = clientCurrent.getCards().stream().filter(card -> card.getType() == cardType && card.isEnabled() == true).collect(Collectors.toSet());

        if ( cards.size() >= 3 ) {
            return new ResponseEntity<>("No puede tener mas de 3 tarjetas asociadas", HttpStatus.FORBIDDEN);
        }
        if ( cardType.equals("") ) {
            return new ResponseEntity<>("Debes elegir el tipo de tarjeta", HttpStatus.FORBIDDEN);
        }
        if ( colorType.equals("") ) {
            return new ResponseEntity<>("Debes elegir un color de tarjeta", HttpStatus.FORBIDDEN);
        }

        String numberCard = CardUtils.getNumberCard();
        int numberCvv = CardUtils.getNumberCvv();

        cardService.saveCard(new Card(clientCurrent.getFirstName() + " " + clientCurrent.getLastName(), cardType, colorType, numberCard, numberCvv, LocalDate.now(), LocalDate.now().plusYears(5), clientCurrent));
        return new ResponseEntity<>("Su tarjeta se ha creado exitosamente", HttpStatus.CREATED);
    }


@PatchMapping("/client/current/cards")
    public ResponseEntity<?> deleteCard(Authentication authentication, @RequestParam Long id){
    Client clientCurrent = clientService.findByEmail(authentication.getName());
    Set <Card> cardCurrent = clientCurrent.getCards();

    if (clientCurrent == null){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    if (id == 0){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }if (id == null){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    Card deleteCard = cardService.findById(id);
    if(deleteCard == null){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    } if (!cardCurrent.contains(deleteCard)){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }
    deleteCard.setEnabled(false);
    cardService.saveCard(deleteCard);
    return new ResponseEntity<>(HttpStatus.OK);
}
    @Transactional
    @PostMapping("/clients/current/cardTransaction")
    public ResponseEntity<?> cardPayment(Authentication authentication, @RequestBody CardTransactionDTO cardTransactionDTO){

        Client client = clientService.findByEmail(authentication.getName());
        Card card = cardService.findByNumber(cardTransactionDTO.getCardNumber());
        List<Account> accounts = client.getAccounts().stream().filter(Account::isAccountEnabled).filter(account -> account.getBalance() >= cardTransactionDTO.getAmount()).collect(Collectors.toList());
       Account account = accounts.stream().min(Comparator.comparing(Account::getId)).orElse(null);
       //account1 -> account1.getId()

        if (account == null){
            return new ResponseEntity<>("La cuenta no existe", HttpStatus.FORBIDDEN);
        }
        if (!client.getAccounts().contains(account)){
            return new ResponseEntity<>("La cuenta no corresponde al cliente", HttpStatus.FORBIDDEN);
        }
        if (!client.getCards().contains(card)){
            return new ResponseEntity<>("La tarjeta no pertenece al cliente", HttpStatus.FORBIDDEN);
        }
        if (cardTransactionDTO.getAmount() > account.getBalance()){
            return new ResponseEntity<>("El monto supera el balance", HttpStatus.FORBIDDEN);
        }
        if (cardTransactionDTO.getCvv() != card.getCvv()){
            return new ResponseEntity<>("Codigo de seguridad invalido", HttpStatus.FORBIDDEN);
        }
        if (card.getThruDate().isBefore(LocalDate.now())){
            return new ResponseEntity<>("Tarjeta expirada", HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction(account, TransactionType.DEBITO, -cardTransactionDTO.getAmount(), cardTransactionDTO.getDescription(), LocalDateTime.now());
        transactionService.saveTransaction(transaction);

        account.setBalance(account.getBalance()-cardTransactionDTO.getAmount());
        accountService.saveAccount(account);

        return new ResponseEntity<>("Pago aceptado", HttpStatus.ACCEPTED);
    }



}
