package com.janning_owns_it.tarot.service;

import com.janning_owns_it.tarot.exception.ApiException;
import com.janning_owns_it.tarot.helper.RedisTTLHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class IpRateLimitService {

    private final RedisTemplate<String, String> redisTemplate;

    public IpRateLimitService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void checkLimit(HttpServletRequest request) {
        checkLimitByIp(getUserIp(request));
        checkLimitByFingerPrint(request);
    }

    private void checkLimitByFingerPrint(HttpServletRequest request) {
        String fingerPrint = getFingerPrint(request);
        if (fingerPrint != null && !fingerPrint.isEmpty()) {
            checkQuota("fingerPrint:" + fingerPrint, 1,
                    "You’ve reached your daily reading limit. Come back tomorrow.");
        }
    }

    private String getFingerPrint(HttpServletRequest request) {
        return request.getHeader("X-Device-Id");
    }

    private void checkLimitByIp(String ip) {
        checkQuota("ip:" + ip, 20, "The oracle is resting for today. Come back tomorrow.");
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

    private void checkQuota(String id, int quota, String message) {
        String currentValue = redisTemplate.opsForValue().get(id);
        int count = currentValue == null ? 0 : Integer.parseInt(currentValue);

        if (count >= quota) {
            throw new ApiException(message, HttpStatus.TOO_MANY_REQUESTS);
        }

        redisTemplate.opsForValue().set(id, String.valueOf(count + 1),
                Duration.ofSeconds(RedisTTLHelper.getSecondsUntilMidnight()));
    }
}


