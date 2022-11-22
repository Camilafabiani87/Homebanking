package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.Service.TransactionService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TransactionServiceImplement implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

//    @Override
//    public Set<Transaction> getTransactionByDate(LocalDateTime dateFrom, LocalDateTime dateTo) {
//        return transactionRepository.findByDateBetween(dateFrom, dateTo);
//    }

    @Override
public Set<Transaction> transactionFilter(LocalDateTime from, LocalDateTime to, Set<Transaction> transactions) {
        return transactions.stream().filter(transaction ->(transaction.getCreationDate()).isAfter(from)  && (transaction.getCreationDate()).isBefore(to)).collect(Collectors.toSet());
    }


}
