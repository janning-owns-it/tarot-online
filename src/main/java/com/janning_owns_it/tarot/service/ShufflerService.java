package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.model.Deck;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ShufflerService {

    public List<String> sortCards() {
        List<String> sortedCards = new ArrayList<>();
        Deck deck = new Deck();
        Map<Integer, String> cards = deck.getCards();
        List<Integer> randomCardNumbers = randomCardNumbers(3, 156);

        for (Integer randomCardNumber : randomCardNumbers) {
            sortedCards.add(cards.get(randomCardNumber));
        }

        return sortedCards;
    }

    private List<Integer> randomCardNumbers(Integer cardsToSort, Integer maxCardsToSort) {
        List<Integer> randomCardNumbers = new ArrayList<>();
        Random random = new Random();

        while (randomCardNumbers.size() < cardsToSort) {
            int number = random.nextInt(maxCardsToSort);
            if (!randomCardNumbers.contains(number)) {
                randomCardNumbers.add(number);
            }
        }

        return randomCardNumbers;
    }

    public String sortedCardsToTextInOrder(List<String> sortedCards) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sortedCards.size(); i++) {
            sb.append(i + 1).append(" -> ").append(sortedCards.get(i)).append("\n");
        }

        return sb.toString();
    }
}
