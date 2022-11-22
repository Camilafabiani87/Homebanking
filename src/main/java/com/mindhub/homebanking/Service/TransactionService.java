package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDateTime;
import java.util.Set;

public interface TransactionService {
    public void saveTransaction(Transaction transaction);
    public Set<Transaction> transactionFilter(LocalDateTime from, LocalDateTime to,Set<Transaction> transactions);

}
