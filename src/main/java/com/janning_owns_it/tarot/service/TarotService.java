package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.component.PromptManager;
import com.janning_owns_it.tarot.model.TarotReadingResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
public class TarotService {

    private ShufflerService shufflerService;

    public TarotService(ShufflerService shufflerService) {
        this.shufflerService = shufflerService;
    }

    public TarotReadingResponse getReading(String querentsQuestion) throws IOException {
        return getArcaneGuideResponse(querentsQuestion);
    }

    private TarotReadingResponse getArcaneGuideResponse(String querentsQuestion) throws IOException {
        Set<String> sortedCards = shufflerService.sortCards();

        TarotReadingResponse response = new TarotReadingResponse();
        response.setArcaneResponse(askToArcaneGuide(querentsQuestion, shufflerService.sortedCardsToTextInOrder(sortedCards)));
        response.setSortedCardsInOrder(sortedCards);

        return response;
    }

    private String askToArcaneGuide(String querentsQuestion, String sortedCardsToTextInOrder) throws IOException {
        new PromptManager().getPrompts(querentsQuestion, sortedCardsToTextInOrder);
        return querentsQuestion + "\n" + sortedCardsToTextInOrder;
    }
}
