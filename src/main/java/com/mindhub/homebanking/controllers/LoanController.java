package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Service.*;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    LoanService loanService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    ClientLoanService clientLoanService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getLoansDTO();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<?> requestLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {
        Client clientCurrent = clientService.findByEmail(authentication.getName());

        if ( clientCurrent != null ) {
            Loan loan = loanService.findById(loanApplicationDTO.getLoanId());
            Account accountDestiny = accountService.findByNumber(loanApplicationDTO.getNumberAccountDestin());

            Set<Loan> loansClientCurrent = clientCurrent.getClientloan().stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toSet());

            if ( loan == null ) {
                return new ResponseEntity<>("El prestamo solicitado no existe", HttpStatus.FORBIDDEN);
            }
            if ( loanApplicationDTO.getNumberAccountDestin().isEmpty() ) {
                return new ResponseEntity<>("La cuenta de destino esta vacia", HttpStatus.FORBIDDEN);
            }
            if ( loanApplicationDTO.getPayment() == null ) {
                return new ResponseEntity<>("Las cuotas estan vacias", HttpStatus.FORBIDDEN);
            }
            if ( loanApplicationDTO.getAmount() <= 0 ) {
                return new ResponseEntity<>("El monto ingresado es menor a cero", HttpStatus.FORBIDDEN);
            }
            if ( accountDestiny == null ) {
                return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
            }
            if ( loanApplicationDTO.getAmount() > loan.getMaxAmount() ) {
                return new ResponseEntity<>("El monto solicitado  supera el monto máximo permitido del préstamo", HttpStatus.FORBIDDEN);
            }
            if ( !clientCurrent.getAccounts().contains(accountDestiny) ) {
                return new ResponseEntity<>("La cuenta de destino no pertenece a  un cliente autenticado", HttpStatus.FORBIDDEN);
            }
            if ( !loan.getPayment().contains(loanApplicationDTO.getPayment()) ) {
                return new ResponseEntity<>("La cantidad de cuotas no esta disponible para el prestamo solicitado", HttpStatus.FORBIDDEN);
            }
            if ( loansClientCurrent.contains(loan) ) {
                return new ResponseEntity<>("No puede solicitar el mismo prestamo", HttpStatus.FORBIDDEN);
            }

            double interest = loanApplicationDTO.getAmount() * 1.2;

            ClientLoan clientLoan = new ClientLoan(interest, loanApplicationDTO.getPayment(), clientCurrent, loan);
            clientLoanService.saveClientLoan((clientLoan));
            Transaction loanTransaction = new Transaction(TransactionType.CREDITO, loanApplicationDTO.getAmount(), "Prestamo " + loan.getName() + " aprobado", LocalDateTime.now(), accountDestiny);
            transactionService.saveTransaction(loanTransaction);
            accountDestiny.setBalance(accountDestiny.getBalance() + loanApplicationDTO.getAmount());
            accountService.saveAccount(accountDestiny);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Para solicitar el prestamo debes estar autenticado", HttpStatus.FORBIDDEN);
    }
}
