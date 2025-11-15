package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.model.TarotReadingResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TarotServiceTest {

    @Test
    void getReadingTest() throws IOException {
        ShufflerService shufflerService = new ShufflerService();
        OpenAiIntegration openAiIntegration = new OpenAiIntegration();
        TarotService tarotService = new TarotService(shufflerService, openAiIntegration);
        String question = "Will I find true love soon?";
        TarotReadingResponse response = tarotService.getReading(question);

        assertNotNull(response);
        assertNotNull(response.getArcaneResponse());
        assertNotNull(response.getSortedCardsInOrder());
        assertEquals(3, response.getSortedCardsInOrder().size());
    }

    @Test
    void validateQuerentsQuestion() {
        TarotService tarotService = new TarotService(null, null);

        assertEquals("The question cannot be null or empty", getExceptionMessage(tarotService, null));
        assertEquals("The question cannot be null or empty", getExceptionMessage(tarotService, ""));
        assertEquals("The question must be less than 1000 characters", getExceptionMessage(tarotService, "a".repeat(1001)));
    }

    private String getExceptionMessage(TarotService tarotService, String question) {
        return assertThrows(
                IllegalArgumentException.class,
                () -> tarotService.getReading(question)
        ).getMessage();
    }
}
