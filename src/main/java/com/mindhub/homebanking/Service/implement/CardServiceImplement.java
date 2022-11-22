package com.mindhub.homebanking.Service.implement;

import com.mindhub.homebanking.Service.CardService;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    CardRepository cardRepository;
    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card findById(long id) {
       return cardRepository.findById(id).orElse(null);
    }

    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }
}
