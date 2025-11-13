package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.component.PromptManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OpenAiIntegrationTest {

    @Test
    public void getArcaneResponseTest() throws IOException {
        OpenAiIntegration openAiIntegration = new OpenAiIntegration();

        String querentsQuestion = "Will I find true love soon?";
        String sortedCardsToTextInOrder = "1 -> Justice - Major Arcana\n" + "2 -> 5 of Swords - Minor Arcana\n" +
                "3 -> 3 of Pentacles - Minor Arcana";

        String arcaneResponse = openAiIntegration.getArcaneResponse(new PromptManager().getPrompts(querentsQuestion,
                sortedCardsToTextInOrder));

        assertNotNull(arcaneResponse);
    }
}
