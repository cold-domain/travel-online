package cn.kmbeast.redis;

import com.alibaba.fastjson2.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Component
public class RedisCacheHelper {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String buildQueryCacheKey(String prefix, Object query) {
        String payload = JSON.toJSONString(query);
        String digest = DigestUtils.md5DigestAsHex(payload.getBytes(StandardCharsets.UTF_8));
        return prefix + digest;
    }

    public void evictByPrefix(String prefix) {
        Set<String> keys = stringRedisTemplate.keys(prefix + "*");
        if (keys == null || keys.isEmpty()) {
            return;
        }
        stringRedisTemplate.delete(keys);
    }
}
