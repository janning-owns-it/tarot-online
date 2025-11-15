package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class IpRateLimitService {

    private final RedisTemplate<String, String> redisTemplate;

    public IpRateLimitService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void checkLimit(HttpServletRequest request) {
        String ip = getUserIp(request);
        String currentValue = redisTemplate.opsForValue().get(ip);
        int count = currentValue == null ? 0 : Integer.parseInt(currentValue);

        if (count >= 3) {
            throw new ApiException("Daily usage limit of 3 requests per IP has been reached.", HttpStatus.TOO_MANY_REQUESTS);
        }

        if (count == 0) {
            redisTemplate.opsForValue().set(ip, "1", getTimeUntilMidnight());
        } else {
            redisTemplate.opsForValue().set(ip, String.valueOf(count + 1));
        }
    }

    private Duration getTimeUntilMidnight() {
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(now, now.toLocalDate().plusDays(1).atStartOfDay());
    }

    private String getUserIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty()) {
            return ip.split(",")[0].trim();
        }

        ip = request.getHeader("X-Real-IP");
        if (ip != null && !ip.isEmpty()) {
            return ip.trim();
        }

        return request.getRemoteAddr();
    }
}


