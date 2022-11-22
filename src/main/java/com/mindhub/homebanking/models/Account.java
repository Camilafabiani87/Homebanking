package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import static javax.persistence.FetchType.EAGER;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;
    private String number;
    private LocalDateTime creationDate;
    private Double Balance;

    private AccountType type;
    private boolean accountEnabled = true;
    @OneToMany(mappedBy = "account", fetch = EAGER)
    Set<Transaction> transactions = new HashSet<>();

    public Account() {
    }

//    public Account(String number, LocalDateTime creationDate, Double balance,AccountType type) {
//        this.number = number;
//        this.creationDate = creationDate;
//        this.Balance = balance;
//        this.type = type;
//    }
//
//    public Account(String number, LocalDateTime creationDate, Double balance, Client client) {
//        this.number = number;
//        this.creationDate = creationDate;
//        this.Balance = balance;
//        this.client = client;
//    }
    public Account(String number, LocalDateTime creationDate, Double balance,AccountType type, Client client) {
        this.number = number;
        this.creationDate = creationDate;
        this.Balance = balance;
        this.type = type;
        this.client = client;
    }


    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public Double getBalance() {
        return Balance;
    }
    public void setBalance(Double balance) {
        Balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    public void setAccountEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", Balance=" + Balance +
                '}';
    }
}

