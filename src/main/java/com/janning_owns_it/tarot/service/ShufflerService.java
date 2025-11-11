package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.model.Deck;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;


@Service
public class ShufflerService {

    public Set<String> sortCards() {
        Set<String> sortedCards = new HashSet<>();
        Deck deck = new Deck();
        Map<Integer, String> cards = deck.getCards();
        Set<Integer> randomCardNumbers = randomCardNumbers(3, 156);

        for (Integer randomCardNumber : randomCardNumbers) {
            sortedCards.add(cards.get(randomCardNumber));
        }

        return sortedCards;
    }

    private Set<Integer> randomCardNumbers(Integer cardsToSort, Integer maxCardsToSort) {
        Set<Integer> randomCardNumbers = new HashSet<>();
        Random random = new Random();

        while (randomCardNumbers.size() < cardsToSort) {
            randomCardNumbers.add(random.nextInt(maxCardsToSort));
        }

        return randomCardNumbers;
    }

    public String sortedCardsToTextInOrder(Set<String> sortedCards) {
        StringBuilder sb = new StringBuilder();

        int count = 1;
        for (String card : sortedCards) {
            sb.append(count++).append(" -> ").append(card).append("\n");
        }

        return sb.toString();
    }
}
