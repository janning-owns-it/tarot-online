package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.exception.ApiException;
import com.janning_owns_it.tarot.model.TarotReadingResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TarotServiceTest {

    private TarotService tarotService;
    private ShufflerService shufflerService;
    private OpenAiIntegration openAiIntegration;
    private IpRateLimitService ipRateLimitService;
    private HttpServletRequest requestMock;
    private RedisTemplate<String, String> redisTemplateMock;

    @BeforeEach
    void setup() {
        requestMock = Mockito.mock(HttpServletRequest.class);
        Mockito.when(requestMock.getHeader("X-Forwarded-For"))
                .thenReturn("123.45.67.89");
        Mockito.when(requestMock.getHeader("X-Real-IP")).thenReturn("123.45.67.89");
        Mockito.when(requestMock.getRemoteAddr()).thenReturn("123.45.67.89");

        redisTemplateMock = Mockito.mock(RedisTemplate.class);
        Mockito.when(redisTemplateMock.opsForValue()).thenReturn(Mockito.mock(ValueOperations.class));

        shufflerService = new ShufflerService();
        openAiIntegration = new OpenAiIntegration();
        ipRateLimitService = new IpRateLimitService(redisTemplateMock);
        tarotService = new TarotService(shufflerService, openAiIntegration, ipRateLimitService);
    }

    @Test
    void getReadingTest() throws IOException {
        String question = "Will I find true love soon?";
        TarotReadingResponse response = tarotService.getReading(question, requestMock);

        assertNotNull(response);
        assertNotNull(response.getArcaneResponse());
        assertNotNull(response.getSortedCardsInOrder());
        assertEquals(3, response.getSortedCardsInOrder().size());
    }

    @Test
    void validateIfQuerentsQuestionIsNull() {
        ApiException apiException = getExceptionMessage(tarotService, null);

        assertEquals("The question cannot be null or empty.", apiException.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, apiException.getStatus());
    }

    @Test
    void validateIfQuerentsQuestionIsEmpty() {
        ApiException apiException = getExceptionMessage(tarotService, "");

        assertEquals("The question cannot be null or empty.", apiException.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, apiException.getStatus());
    }

    @Test
    void validateIfQuerentsQuestionIsMoreThan1000Characters() {
        ApiException apiException = getExceptionMessage(tarotService, "a".repeat(1001));

        assertEquals("The question must be less than 1000 characters.", apiException.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, apiException.getStatus());
    }

    private ApiException getExceptionMessage(TarotService tarotService, String question) {
        return assertThrows(
                ApiException.class,
                () -> tarotService.getReading(question, requestMock)
        );
    }
}
