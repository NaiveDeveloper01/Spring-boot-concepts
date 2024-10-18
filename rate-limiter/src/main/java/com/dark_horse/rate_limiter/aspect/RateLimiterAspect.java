package com.dark_horse.rate_limiter.aspect;

import com.dark_horse.rate_limiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Aspect
@Component
public class RateLimiterAspect {
    @Autowired
    private RedisTemplate<String, Object> template;

    @Pointcut("@within(rateLimiter)")
    public void rateLimitClass(RateLimiter rateLimiter) {
    }

    @Around("rateLimitClass(rateLimiter)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimiter rateLimiter) throws Throwable {
        String clientId = getClientIdFromRequest();
        String key = "Key" + rateLimiter.getClass()+ clientId;
        Long count = template.opsForValue().increment(key, 1);

        if (count == 1) {
            template.expireAt(key, Instant.now().plus(rateLimiter.windowInSeconds(), ChronoUnit.SECONDS));
        }
        if (count > rateLimiter.limit())
            throw new Exception("Exceeded");
        return joinPoint.proceed();
    }

    private String getClientIdFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-Client-Id");  // Example: Extract client ID from headers
    }

}
