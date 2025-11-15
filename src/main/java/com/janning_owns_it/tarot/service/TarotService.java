package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.component.PromptManager;
import com.janning_owns_it.tarot.exception.ApiException;
import com.janning_owns_it.tarot.model.TarotReadingResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Service
public class TarotService {

    private ShufflerService shufflerService;
    private OpenAiIntegration openAiIntegration;
    private IpRateLimitService ipRateLimitService;

    public TarotService(ShufflerService shufflerService, OpenAiIntegration openAiIntegration,
                        IpRateLimitService ipRateLimitService) {
        this.shufflerService = shufflerService;
        this.openAiIntegration = openAiIntegration;
        this.ipRateLimitService = ipRateLimitService;
    }

    public TarotReadingResponse getReading(String querentsQuestion, HttpServletRequest request) throws IOException {
        validateQuerentsQuestion(querentsQuestion);
        ipRateLimitService.checkLimit(request);
        return getReadingResponse(querentsQuestion);
    }

    private TarotReadingResponse getReadingResponse(String querentsQuestion) throws IOException {
        Set<String> sortedCards = shufflerService.sortCards();

        TarotReadingResponse response = new TarotReadingResponse();
        response.setArcaneResponse(askToArcaneGuide(querentsQuestion, shufflerService.sortedCardsToTextInOrder(sortedCards)));
        response.setSortedCardsInOrder(sortedCards);

        return response;
    }

    private String askToArcaneGuide(String querentsQuestion, String sortedCardsToTextInOrder) throws IOException {
        Map<String, String> prompts = new PromptManager().getPrompts(querentsQuestion, sortedCardsToTextInOrder);
        return openAiIntegration.getArcaneResponse(prompts);
    }

    private void validateQuerentsQuestion(String querentsQuestion) {
        if (querentsQuestion == null || querentsQuestion.isEmpty()) {
            throw new ApiException("The question cannot be null or empty.", HttpStatus.BAD_REQUEST);
        }
        if (querentsQuestion.length() > 1000) {
            throw new ApiException("The question must be less than 1000 characters.", HttpStatus.BAD_REQUEST);
        }
    }
}
