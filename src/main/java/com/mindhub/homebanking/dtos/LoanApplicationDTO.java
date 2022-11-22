package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private long loanId;
    private double amount;
    private Integer payment;
    private String numberAccountDestin;

//    public LoanApplicationDTO(long loanId, double amount, Integer payment, String numberAccountDestin) {
//        this.loanId = loanId;
//        this.amount = amount;
//        this.payment = payment;
//        this.numberAccountDestin = numberAccountDestin;
//    }

    public long getLoanId() {return loanId;}

    public double getAmount() {return amount;}
    public Integer getPayment() {return payment;}
    public String getNumberAccountDestin() {return numberAccountDestin;}
}
