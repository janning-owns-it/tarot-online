package com.janning_owns_it.tarot.config;

import io.github.cdimascio.dotenv.Dotenv;

public class OpenAiConfig {

    private static final Dotenv dotenv = loadDotenv();

    public static final String API_KEY = getEnv("OPENAI_API_KEY");
    public static final String MODEL = getEnv("OPENAI_CHAT_MODEL");
    public static final int MAX_TOKENS = Integer.parseInt(getEnv("OPENAI_MAX_TOKENS"));

    private static Dotenv loadDotenv() {
        try {
            return Dotenv.load();
        } catch (Exception e) {
            return null;
        }
    }

    private static String getEnv(String key) {
        String value = System.getenv(key);
        if (value != null && !value.isEmpty()) {
            return value;
        }

        if (dotenv != null) {
            value = dotenv.get(key);
            if (value != null && !value.isEmpty()) {
                return value;
            }
        }

        throw new IllegalStateException("Environment variable " + key + " not found");
    }
}