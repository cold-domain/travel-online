package cn.kmbeast.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    int count();

    int seconds();

    String message() default "请求过于频繁，请稍后再试";

    RateLimitTarget target() default RateLimitTarget.IP;

    String key() default "";
}
