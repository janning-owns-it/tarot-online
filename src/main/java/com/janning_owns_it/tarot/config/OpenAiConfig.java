package com.janning_owns_it.tarot.config;

import io.github.cdimascio.dotenv.Dotenv;

public class OpenAiConfig {
    private static final Dotenv dotenv = Dotenv.load();

    public static final String API_KEY = dotenv.get("OPENAI_API_KEY");
    public static final String MODEL = dotenv.get("OPENAI_CHAT_MODEL");
    public static final int MAX_TOKENS = Integer.parseInt(dotenv.get("OPENAI_MAX_TOKENS"));
}