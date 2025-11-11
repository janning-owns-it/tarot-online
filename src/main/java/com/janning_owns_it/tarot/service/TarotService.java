package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.component.PromptManager;
import com.janning_owns_it.tarot.model.TarotReadingResponse;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
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
        OpenAIClient openAIClient = OpenAIOkHttpClient.builder()
                .apiKey("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")
                .build();
        Map<String, String> promptManager = new PromptManager().getPrompts(querentsQuestion, sortedCardsToTextInOrder);

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.of("gpt-3.5-turbo"))
                .addSystemMessage(promptManager.get("system"))
                .addUserMessage(promptManager.get("user"))
                .maxTokens(1000)
                .build();

        ChatCompletion completion = openAIClient.chat().completions().create(params);

        return completion.choices().stream()
                    .flatMap(choice -> choice.message().content().stream())
                    .findFirst()
                    .orElse("No response from Arcane Guide");
    }
}
