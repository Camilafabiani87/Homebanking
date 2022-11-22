package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Service.AccountService;
import com.mindhub.homebanking.Service.ClientService;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getClient() {
        return clientService.getClientsDTO();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClientDTO(id);
    }

//    public long getRandomNumber() {
//        return (long) ((Math.random() * (100000000 - 100000)) + 100000);
//    }

    @PostMapping("/clients")
    public ResponseEntity<?> register(

            @RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {

        if ( firstName.isEmpty() ) {
            return new ResponseEntity<>("Missing firstName", HttpStatus.FORBIDDEN);
        }
        if ( lastName.isEmpty() ) {
            return new ResponseEntity<>("Missing lastName", HttpStatus.FORBIDDEN);
        }
        if ( email.isEmpty() ) {
            return new ResponseEntity<>("Missing email", HttpStatus.FORBIDDEN);
        }
        if ( password.isEmpty() ) {
            return new ResponseEntity<>("Missing password", HttpStatus.FORBIDDEN);
        }
        if ( clientService.findByEmail(email) != null ) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);
        Account account = new Account("Vin" + AccountUtils.getRandomNumber(), LocalDateTime.now(), 0.0,AccountType.AHORRO ,client);
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getClientCurrent(Authentication authentication) {
        return new ClientDTO(clientService.findByEmail(authentication.getName()));
    }

}
