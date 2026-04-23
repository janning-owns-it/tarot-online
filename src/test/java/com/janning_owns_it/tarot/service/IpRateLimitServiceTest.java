package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

    private Map<String, String> store;

    @BeforeEach
    void setup() {
        requestMock = Mockito.mock(HttpServletRequest.class);

        store = new HashMap<>();
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
    void checkLimitByFingerprint() {
        Mockito.when(requestMock.getHeader("X-Device-Id")).thenReturn("fingerPrint-Test");

        simulateRequests(1);

        ApiException apiException = assertThrows(
                ApiException.class,
                () -> ipRateLimitService.checkLimit(requestMock)
        );

        assertEquals("You’ve reached your daily reading limit. Come back tomorrow.", apiException.getMessage());
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, apiException.getStatus());
    }

    @Test
    void checkLimitByIp() {
        Mockito.when(requestMock.getRemoteAddr()).thenReturn("123.45.67.89");
        Mockito.when(requestMock.getHeader("X-Device-Id")).thenReturn(null);

        simulateRequests(20);

        ApiException apiException = assertThrows(
                ApiException.class,
                () -> ipRateLimitService.checkLimit(requestMock)
        );

        assertEquals("The oracle is resting for today. Come back tomorrow.", apiException.getMessage());
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, apiException.getStatus());
    }

    @Test
    void checkLimitIfXForwardedFor() {
        Mockito.when(requestMock.getHeader("X-Forwarded-For")).thenReturn("123.45.67.89");

        simulateRequests(20);

        ApiException apiException = assertThrows(
                ApiException.class,
                () -> ipRateLimitService.checkLimit(requestMock)
        );

        assertEquals("The oracle is resting for today. Come back tomorrow.", apiException.getMessage());
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, apiException.getStatus());
    }

    @Test
    void checkLimitIfXrealIp() {
        Mockito.when(requestMock.getHeader("X-Real-IP")).thenReturn("123.45.67.89");

        simulateRequests(20);

        ApiException apiException = assertThrows(
                ApiException.class,
                () -> ipRateLimitService.checkLimit(requestMock)
        );

        assertEquals("The oracle is resting for today. Come back tomorrow.", apiException.getMessage());
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, apiException.getStatus());
    }

    private void simulateRequests(int quantity) {
        for (int i = 0; i < quantity; i++) {
            ipRateLimitService.checkLimit(requestMock);
        }
    }
}