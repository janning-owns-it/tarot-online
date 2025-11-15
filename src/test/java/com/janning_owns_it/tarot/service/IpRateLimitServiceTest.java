package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IpRateLimitServiceTest {

    private IpRateLimitService ipRateLimitService;
    private HttpServletRequest requestMock;
    private RedisTemplate<String, String> redisTemplateMock;

    @BeforeEach
    void setup() {
        requestMock = Mockito.mock(HttpServletRequest.class);

        Map<String, String> store = new HashMap<>();
        ValueOperations<String, String> valueOps = Mockito.mock(ValueOperations.class);

        Mockito.when(valueOps.get(Mockito.anyString()))
                .thenAnswer(invocation -> store.get(invocation.getArgument(0)));

        Mockito.doAnswer(invocation -> {
            String key = invocation.getArgument(0);
            String value = invocation.getArgument(1);
            store.put(key, value);
            return null;
        }).when(valueOps).set(Mockito.anyString(), Mockito.anyString(), Mockito.any(Duration.class));

        redisTemplateMock = Mockito.mock(RedisTemplate.class);
        Mockito.when(redisTemplateMock.opsForValue()).thenReturn(valueOps);

        ipRateLimitService = new IpRateLimitService(redisTemplateMock);
    }

    @Test
    void checkLimit() {
        Mockito.when(requestMock.getRemoteAddr()).thenReturn("123.45.67.89");

        simulateRequests();

        ApiException apiException = getException();
        assertEquals("Daily usage limit of 3 requests per IP has been reached.", apiException.getMessage());
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, apiException.getStatus());
    }

    @Test
    void checkLimitIfXForwardedFor() {
        Mockito.when(requestMock.getHeader("X-Forwarded-For"))
                .thenReturn("123.45.67.89");

        simulateRequests();

        ApiException apiException = getException();
        assertEquals("Daily usage limit of 3 requests per IP has been reached.", apiException.getMessage());
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, apiException.getStatus());
    }

    @Test
    void checkLimitIfXrealIp() {
        Mockito.when(requestMock.getHeader("X-Real-IP")).thenReturn("123.45.67.89");

        simulateRequests();

        ApiException apiException = getException();
        assertEquals("Daily usage limit of 3 requests per IP has been reached.", apiException.getMessage());
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, apiException.getStatus());
    }

    private ApiException getException() {
        return assertThrows(
                ApiException.class,
                () -> ipRateLimitService.checkLimit(requestMock)
        );
    }

    private void simulateRequests() {
        for (int i = 0; i < 3; i++) {
            ipRateLimitService.checkLimit(requestMock);
        }
    }
}
