package com.janning_owns_it.tarot.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class OpenAiIntegration {

    @Value("${openai.api-key}")
    private String apiKey;
    @Value("${openai.max-tokens}")
    private int apiMaxTokens;
    @Value("${openai.chat-model}")
    private String chatModel;

    public String getArcaneResponse(Map<String, String> prompts) throws IOException {
        OpenAIClient openAIClient = OpenAIOkHttpClient.builder().apiKey(apiKey).build();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.of(chatModel))
                .addSystemMessage(prompts.get("system"))
                .addUserMessage(prompts.get("user"))
                .maxTokens(apiMaxTokens)
                .build();

        ChatCompletion completion = openAIClient.chat().completions().create(params);

        return completion.choices().stream()
                .flatMap(choice -> choice.message().content().stream())
                .findFirst()
                .orElse("No response from Arcane Guide");
    }
}
