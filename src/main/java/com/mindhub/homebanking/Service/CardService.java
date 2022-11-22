package com.mindhub.homebanking.Service;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Loan;

public interface CardService {
    public void saveCard(Card card);

    public Card findById(long id);
    Card findByNumber(String number);

}
