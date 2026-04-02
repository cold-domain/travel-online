package cn.kmbeast.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLockHelper {

    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT = new DefaultRedisScript<Long>(
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) " +
                    "else return 0 end",
            Long.class
    );

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public boolean tryLock(String key, String value, long timeout, TimeUnit timeUnit) {
        Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
        return Boolean.TRUE.equals(locked);
    }

    public void unlock(String key, String value) {
        stringRedisTemplate.execute(UNLOCK_SCRIPT, Collections.singletonList(key), value);
    }
}
