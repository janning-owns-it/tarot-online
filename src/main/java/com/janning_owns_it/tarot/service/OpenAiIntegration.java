package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.config.OpenAiConfig;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class OpenAiIntegration {

    private OpenAiConfig openAiConfig = new OpenAiConfig();

    public String getArcaneResponse(Map<String, String> prompts) throws IOException {
        OpenAIClient openAIClient = OpenAIOkHttpClient.builder().apiKey(openAiConfig.API_KEY).build();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.of(openAiConfig.MODEL))
                .addSystemMessage(prompts.get("system"))
                .addUserMessage(prompts.get("user"))
                .maxTokens(openAiConfig.MAX_TOKENS)
                .build();

        ChatCompletion completion = openAIClient.chat().completions().create(params);

        return completion.choices().stream()
                .flatMap(choice -> choice.message().content().stream())
                .findFirst()
                .orElse("No response from Arcane Guide");
    }
}
