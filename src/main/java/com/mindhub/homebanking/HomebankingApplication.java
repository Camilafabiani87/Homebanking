package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
//@Autowired
//private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);


	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository,LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,CardRepository cardRepository){
		return args -> {
//			Client client1 = new Client("Melba","Morel","melba@gmail.com",passwordEncoder.encode("123"));
//			clientRepository.save(client1);
//			Client client2 = new Client("Tobias","Perez","tobiasperez@gmail.com",passwordEncoder.encode("123"));
//			clientRepository.save(client2);
////			Client admin = new Client("Admin","Admin","admin@admin.com", passwordEncoder.encode("1234"));
////			clientRepository.save(admin);
//			Account account1 = new Account("Vin001", LocalDateTime.now(), 5000.0,AccountType.AHORRO,client1);
//			Account account2 = new Account("Vin002", LocalDateTime.now().plusDays(1), 7500.0,AccountType.AHORRO,client1);
//			Account account3 = new Account("Vin003", LocalDateTime.now().plusDays(1), 7500.0,AccountType.AHORRO,client2);
//			accountRepository.save(account1);
//			accountRepository.save(account2);
//			accountRepository.save(account3);
//
////			client1.addAccount(account1);
////			client1.addAccount(account2);
//
//			Transaction transaction1 = new Transaction(TransactionType.CREDITO, 20000.0, "Fly Bondy",LocalDateTime.now(),account1);
//			Transaction transaction2 = new Transaction(TransactionType.DEBITO, -3000.0, "Cartera", LocalDateTime.now().plusDays(1),account1);
//			Transaction transaction3 = new Transaction(TransactionType.CREDITO, 70000.0, "Heladera",LocalDateTime.now().plusDays(2),account1);
//			Transaction transaction4 = new Transaction(TransactionType.DEBITO, -1000.0, "Netflix", LocalDateTime.now().plusDays(6),account1);
//			Transaction transaction5 = new Transaction(TransactionType.CREDITO, 50000.0, "Travel House",LocalDateTime.now().plusDays(4),account2);
//			Transaction transaction6 = new Transaction(TransactionType.CREDITO, 4000.0, "Ateneo", LocalDateTime.now().plusDays(5),account1);
//			Transaction transaction7 = new Transaction(TransactionType.CREDITO, 30000.0, "De Viaje.ok",LocalDateTime.now().plusDays(6),account2);
//			Transaction transaction8 = new Transaction(TransactionType.DEBITO, -1000.0, "Spotify", LocalDateTime.now().plusDays(7),account2);
//
//
//
//
//			transactionRepository.save(transaction1);
//			transactionRepository.save(transaction2);
//			transactionRepository.save(transaction3);
//			transactionRepository.save(transaction4);
//			transactionRepository.save(transaction5);
//			transactionRepository.save(transaction6);
//			transactionRepository.save(transaction7);
//			transactionRepository.save(transaction8);
//
//			Loan loan1 = new Loan("Hipotecario",500000.0,new ArrayList<>(List.of(12, 24, 36,48,60)));
//			Loan loan2 = new Loan("Personal",100000.0,new ArrayList<>(List.of(6, 12, 24)));
//			Loan loan3 = new Loan("Automotriz",300000.0,new ArrayList<>(List.of(6, 12, 24,36)));
//			loanRepository.save(loan1);
//			loanRepository.save(loan2);
//			loanRepository.save(loan3);
//
//			ClientLoan clientLoan1 = new ClientLoan(400000.0,60,client1,loan1);
//			ClientLoan clientLoan2 = new ClientLoan(50000.0,12,client1,loan2);
//			ClientLoan clientLoan3 = new ClientLoan(100000.0,24,client2,loan2);
//			ClientLoan clientLoan4 = new ClientLoan(200000.0,36,client2,loan3);
//			clientLoanRepository.save(clientLoan1);
//			clientLoanRepository.save(clientLoan2);
//			clientLoanRepository.save(clientLoan3);
//			clientLoanRepository.save(clientLoan4);
//
//
//			Card card1 = new Card("melba morel",CardType.DEBITO,ColorType.GOLD,"4567" + " " + "8598" + " " + "2432" + " " + "7070",322,LocalDate.now(),LocalDate.now().plusYears(5),client1 );
//			Card card2 = new Card("Melba Morel",CardType.CREDITO,ColorType.TITANIUM,"5587" + " " + "1276" + " " + "4192" + " " + "2260",499,LocalDate.now(),LocalDate.now().plusYears(5),client1 );
//			Card card3 = new Card("Tobias perez",CardType.CREDITO,ColorType.SILVER,"4592" + " " + "3328" + " " + "1674" + " " + "8787",233,LocalDate.now(),LocalDate.now().plusYears(5),client2 );
//			cardRepository.save(card1);
//			cardRepository.save(card2);
//			cardRepository.save(card3);












		};
	}

}
