package com.janning_owns_it.tarot.component;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@Component
public class PromptManager {

    public Map<String, String> getPrompts(String querentsQuestion, String sortedCardsToTextInOrder) throws IOException {
        return Map.of(
                "system", getSystemPrompt(), "user", querentsQuestion + "\n" + sortedCardsToTextInOrder
        );
    }

    private String getSystemPrompt() throws IOException {
        Resource resource = new ClassPathResource("templates/prompts/systemPromptTemplate.txt");

        return Files.readString(resource.getFile().toPath());
    }
}
