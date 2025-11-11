package com.janning_owns_it.tarot.service;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ShufflerServiceTest {

    @Test
    void sortCardsTest() {
        ShufflerService shufflerService = new ShufflerService();

        Set<String> sortedCards = shufflerService.sortCards();

        assertNotNull(sortedCards);
        assertEquals(3, sortedCards.size());
    }

    @Test
    void shuffleCardsTest() {
        ShufflerService shufflerService = new ShufflerService();
        String cardsTextInOrder = "";
        Set<String> sortedCards = shufflerService.sortCards();
        String cardsToTextInOrder = shufflerService.sortedCardsToTextInOrder(sortedCards);

        assertNotNull(cardsToTextInOrder);

        int cardOrderCounter = 1;
        for (String card : sortedCards) {
            cardsTextInOrder += cardOrderCounter + " -> " + card + "\n";
            cardOrderCounter++;
        }

        assertEquals(cardsToTextInOrder, cardsTextInOrder);
    }
}
