package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private Double Balance;

    private AccountType type;
    Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO() {
    }
    public AccountDTO(Account account) {
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.Balance = account.getBalance();
        this.id = account.getId();
        this.type = account.getType();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Double getBalance() {
        return Balance;
    }

    public AccountType getType() {return type;}

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

}
