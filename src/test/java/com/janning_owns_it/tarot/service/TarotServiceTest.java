package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.model.TarotReadingResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TarotServiceTest {

    @Test
    void getReadingTest() throws IOException {
        ShufflerService shufflerService = new ShufflerService();
        TarotService tarotService = new TarotService(shufflerService);
        String question = "Will I find true love soon?";
        TarotReadingResponse response = tarotService.getReading(question);

        assertNotNull(response);
        assertNotNull(response.getArcaneResponse());
        assertNotNull(response.getSortedCardsInOrder());
        assertEquals(3, response.getSortedCardsInOrder().size());
    }
}
