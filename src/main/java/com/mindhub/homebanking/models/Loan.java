package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static javax.persistence.FetchType.EAGER;
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private double maxAmount;
    @ElementCollection
    @Column(name = "payment")
    private List<Integer> payment = new ArrayList<>();
    @OneToMany(mappedBy = "loan", fetch = EAGER)
    Set<ClientLoan> clientLoan = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, double maxAmount, List<Integer> payment) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payment = payment;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }
    public List<Integer> getPayment() {
        return payment;
    }

    public void setPayment(List<Integer> payment) {
        this.payment = payment;
    }

    public Set<ClientLoan> getClientLoan() {
        return clientLoan;
    }

    public void setClientLoan(Set<ClientLoan> clientLoan) {
        this.clientLoan = clientLoan;
    }

    @JsonIgnore
    public Set<Client> getClient() {
        return clientLoan.stream().map(client -> client.getClient()).collect(Collectors.toSet());
    }
    //hace referencia al set de one to many clientLoan

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxAmount=" + maxAmount +
                ", payment=" + payment +
                ", clientLoan=" + clientLoan +
                '}';
    }
}
