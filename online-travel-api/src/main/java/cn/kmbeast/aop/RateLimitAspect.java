package cn.kmbeast.aop;

import cn.kmbeast.constant.RedisKeyConstants;
import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class RateLimitAspect {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Around("@annotation(rateLimit)")
    public Object limit(ProceedingJoinPoint proceedingJoinPoint, RateLimit rateLimit) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return proceedingJoinPoint.proceed();
        }
        HttpServletRequest request = attributes.getRequest();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodKey = StringUtils.hasText(rateLimit.key())
                ? rateLimit.key()
                : signature.getDeclaringType().getSimpleName() + ":" + signature.getMethod().getName();
        String identity = resolveIdentity(request, rateLimit.target());
        String key = RedisKeyConstants.RATE_LIMIT_PREFIX + methodKey + ":" + rateLimit.target() + ":" + identity;
        Long times = stringRedisTemplate.opsForValue().increment(key);
        if (times != null && times == 1L) {
            stringRedisTemplate.expire(key, rateLimit.seconds(), TimeUnit.SECONDS);
        }
        if (times != null && times > rateLimit.count()) {
            return ApiResult.error(rateLimit.message());
        }
        return proceedingJoinPoint.proceed();
    }

    private String resolveIdentity(HttpServletRequest request, RateLimitTarget target) {
        if (target == RateLimitTarget.USER) {
            Integer userId = null;
            try {
                userId = LocalThreadHolder.getUserId();
            } catch (Exception ignored) {
            }
            if (userId == null) {
                String token = request.getHeader("token");
                Claims claims = JwtUtil.fromToken(token);
                if (claims != null) {
                    userId = claims.get("id", Integer.class);
                }
            }
            if (userId != null) {
                return String.valueOf(userId);
            }
        }
        String forwarded = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(forwarded)) {
            String[] parts = forwarded.split(",");
            if (parts.length > 0 && StringUtils.hasText(parts[0])) {
                return parts[0].trim();
            }
        }
        return request.getRemoteAddr();
    }
}
