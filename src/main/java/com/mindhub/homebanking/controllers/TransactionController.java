package com.mindhub.homebanking.controllers;


import com.lowagie.text.DocumentException;
import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.Service.PDFService;
import com.mindhub.homebanking.Service.TransactionService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    @Autowired
    PDFService pdfService;
LocalDateTime localDateNow = LocalDateTime.now();
LocalDateTime localDateMinus = LocalDateTime.now().minusDays(7);

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<?> makeTransaction(Authentication authentication, @RequestParam Double amount, @RequestParam String description, @RequestParam String accountOrigin, @RequestParam String accountDestin) {
        Client clientCurrent = clientService.findByEmail(authentication.getName());
        Account newAccountOrigin = accountService.findByNumber(accountOrigin);
        Account newAccountDestin = accountService.findByNumber(accountDestin);
        Set<Account> accountExist = clientCurrent.getAccounts().stream().filter(account -> account.getNumber().equals(accountOrigin)).collect(Collectors.toSet());

        if ( amount <= 0 ) {
            return new ResponseEntity<>("Falta completar el monto", HttpStatus.FORBIDDEN);
        }
        if ( description.isEmpty() ) {
            return new ResponseEntity<>("Falta completar la descripcion", HttpStatus.FORBIDDEN);
        }
        if ( accountOrigin.isEmpty() ) {
            return new ResponseEntity<>("Falta elegir una cuenta de origen", HttpStatus.FORBIDDEN);
        }
        if ( accountDestin.isEmpty() ) {
            return new ResponseEntity<>("Falta elegir una cuenta destino", HttpStatus.FORBIDDEN);
        }

        if ( accountOrigin.equals(accountDestin) ) {
            return new ResponseEntity<>("La cuenta origen no puede ser igual a la de destino", HttpStatus.FORBIDDEN);
        }

        if ( accountExist.isEmpty() ) {
            return new ResponseEntity<>("No se pudo obtener la cuenta", HttpStatus.FORBIDDEN);
        }

        if ( newAccountOrigin == null ) {
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }

        if ( newAccountOrigin.getBalance() < amount ) {
            return new ResponseEntity<>("Sus fondos son insuficientes para realizar la transferencia", HttpStatus.FORBIDDEN);
        }
        Transaction transactionOrigin = new Transaction(TransactionType.DEBITO, -amount, description + " " + "para " + newAccountDestin.getNumber(), localDateNow, newAccountOrigin);
        Transaction transactionDestin = new Transaction(TransactionType.CREDITO, amount, description + " " + "desde  " + newAccountOrigin.getNumber(), localDateNow, newAccountDestin);
        transactionService.saveTransaction(transactionOrigin);
        transactionService.saveTransaction(transactionDestin);

        newAccountOrigin.setBalance(newAccountOrigin.getBalance() - amount);
        newAccountDestin.setBalance(newAccountDestin.getBalance() + amount);
        accountService.saveAccount(newAccountOrigin);
        accountService.saveAccount(newAccountDestin);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    LocalDateTime localDateTimeFrom = LocalDateTime.now().minusDays(7);
    String accountNumber;

    @PostMapping("/pdf")
    public ResponseEntity<?> getPdf(Authentication authentication,@RequestParam String number,@RequestParam String localDateMinus){
        Client clientCurrent = clientService.findByEmail(authentication.getName());
        accountNumber = number;
        if(localDateMinus.equals("semana")){
            localDateTimeFrom = LocalDateTime.now().minusDays(7);
        }
      return new ResponseEntity<>(HttpStatus.OK);
    };

    @GetMapping("/pdf")
    public void pdf(HttpServletResponse response)throws DocumentException, IOException {

        Account account = accountService.findByNumber(accountNumber);
        Set<Transaction> transactions = account.getTransactions();
        Set<Transaction> transaccionesFiltradas = transactionService.transactionFilter(localDateTimeFrom, localDateNow, transactions);
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValues = "attachment; filename=pdf_Transacciones.pdf";
        response.setHeader(headerKey, headerValues);
        pdfService.export(account, transaccionesFiltradas, response);
    }



}

