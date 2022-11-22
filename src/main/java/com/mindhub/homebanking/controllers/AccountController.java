package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.AccountUtils;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;

    @GetMapping("/clients/accounts")
    public List<AccountDTO> getAccount() {
        return accountService.getAccountSDTO();
    }

    @GetMapping("/clients/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountService.getAccountDTO(id);
    }

//    @Autowired
//    private ClientRepository clientRepository;




    @PostMapping("/clients/current/accounts")
    public ResponseEntity<?> createAccount(Authentication authentication,@RequestParam AccountType accountType) {
        Client clientCurrent = clientService.findByEmail(authentication.getName());
        if(accountType == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        long number = AccountUtils.getRandomNumber();
        if ( clientCurrent.getAccounts().stream().filter(account -> account.isAccountEnabled()).count() >= 3 ) {
            return new ResponseEntity<>("No puede tener mas de 3 cuentas asociadas", HttpStatus.FORBIDDEN);
        } else {
            accountService.saveAccount(new Account("Vin" + number, LocalDateTime.now(), 0.0,accountType,clientCurrent));
            return new ResponseEntity<>("Su cuenta se ha creado exitosamente", HttpStatus.CREATED);
        }
    }
    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<?> deleteAccount(Authentication authentication, @RequestParam Long id){
        Client clientCurrent = clientService.findByEmail(authentication.getName());
        Set <Account> accountCurrent = clientCurrent.getAccounts();
        Account account = accountService.getAccountId(id);
//        if (clientCurrent == null){
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        if (id == 0){
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }if (id == null){
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
        if (account.getBalance() >0){
            return new ResponseEntity<>("La cuenta tiene que estar vacia para borrarla", HttpStatus.FORBIDDEN);
        }
        Account deleteAccount = accountService.getAccountId(id);
        deleteAccount.setAccountEnabled(false);
        accountService.saveAccount(deleteAccount);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}