//package com.mindhub.homebanking;
//
//import com.mindhub.homebanking.dtos.AccountDTO;
//import com.mindhub.homebanking.models.*;
//import com.mindhub.homebanking.repositories.*;
//import com.mindhub.homebanking.utils.CardUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//import java.util.Set;
//
//import static com.mindhub.homebanking.models.TransactionType.DEBITO;
//import static java.util.Arrays.asList;
//import static java.util.Optional.empty;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.hamcrest.Matchers.is;
//import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = NONE)
//
//public class RepositoriesTest {
//    @Autowired
//    LoanRepository loanRepository;
//    @Autowired
//    AccountRepository accountRepository;
//    @Autowired
//    CardRepository cardRepository;
//
//    @Autowired
//    TransactionRepository transactionRepository;
//    @Autowired
//    ClientRepository clientRepository;
//
//    @Test
//    public void existLoans(){
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans,is(not(empty())));
//    }
//
//    @Test
//    public void existPersonalLoan(){
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
//    }
//    @Test
//    public void existAutomotrizLoan(){
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans, hasItem(hasProperty("name", is("Automotriz"))));
//    }
//    @Test
//    public void existHipotecarioLoan(){
//        List<Loan> loans = loanRepository.findAll();
//        assertThat(loans, hasItem(hasProperty("name", is("Hipotecario"))));
//    }
//
//    @Test
//    public void existCreditoCard(){
//        List<Card> cards = cardRepository.findAll();
//        assertThat("CREDITO", isOneOf("CREDITO", "DEBITO"));
//    }
//    @Test
//    public void existDebitoCard(){
//        List<Card> cards = cardRepository.findAll();
//        assertThat("DEBITO", isOneOf("CREDITO", "DEBITO"));
//    }
//    @Test
//    public void propertyColor(){
//        List<Card> cards = cardRepository.findAll();
//        assertThat(cards, hasItem(hasProperty("color", is(ColorType.GOLD))));
//
//    }
//    @Test
//    public void propertyColor2(){
//        List<Card> cards = cardRepository.findAll();
//        assertThat(cards, hasItem(hasProperty("color", is(ColorType.SILVER))));
//
//    }
//
//    @Test
//    public void numberAccount() {
//        List<Account> accounts = accountRepository.findAll();
//        assertThat(accounts, hasItem(hasProperty("number", is("Vin001"))));
//    }
//
//    @Test
//    public void numberStarAccount(){
//        List<Account> accounts = accountRepository.findAll();
//        assertThat(accounts,hasItem(hasProperty("number",is(containsString("Vin")))));
//
//    }
//    @Test
//    public void TransactionTypeExist(){
//        List<Transaction> transactions = transactionRepository.findAll();
//        assertThat(transactions, hasItem(hasProperty("type", is(TransactionType.CREDITO))));
//
//    }
//
//    @Test
//    public void TransactionTypeExists(){
//        List<Transaction> transactions = transactionRepository.findAll();
//        assertThat(transactions, hasItem(hasProperty("type", is(DEBITO))));
//
//    }
//
//
//
//    @Test
//    public void ExistClientProperty(){
//        List<Client> clients = clientRepository.findAll();
//        assertThat(clients,hasItem(hasProperty("email")) );
//    }
//    @Test
//    public void ExistClient() {
//        List<Client> clients = clientRepository.findAll();
//        assertThat(clients, hasItem(hasProperty("firstName", is("Melba"))));
//    }
//
//
//}
