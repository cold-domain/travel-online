package cn.kmbeast.Interceptor;

import cn.kmbeast.constant.RedisKeyConstants;
import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.utils.JwtUtil;
import com.alibaba.fastjson2.JSONObject;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

@Component
public class RedisJwtInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisJwtInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login")
                || requestURI.contains("/error")
                || requestURI.contains("/file")
                || requestURI.contains("/register")) {
            return true;
        }
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            writeError(response, "韬唤璁よ瘉寮傚父锛岃鍏堢櫥褰?");
            return false;
        }
        Claims claims = JwtUtil.fromToken(token);
        if (claims == null) {
            writeError(response, "韬唤璁よ瘉寮傚父锛岃鍏堢櫥褰?");
            return false;
        }
        if (StringUtils.hasText(claims.getId())
                && Boolean.TRUE.equals(stringRedisTemplate.hasKey(RedisKeyConstants.tokenBlacklistKey(claims.getId())))) {
            writeError(response, "鐧诲綍宸插け鏁堬紝璇烽噸鏂扮櫥褰?");
            return false;
        }
        Integer userId = claims.get("id", Integer.class);
        Integer roleId = claims.get("role", Integer.class);
        String activeToken = stringRedisTemplate.opsForValue().get(RedisKeyConstants.userTokenKey(userId));
        if (!StringUtils.hasText(activeToken) || !token.equals(activeToken)) {
            writeError(response, "褰撳墠鐧诲綍宸插け鏁堬紝璇烽噸鏂扮櫥褰?");
            return false;
        }
        LocalThreadHolder.setUserId(userId, roleId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LocalThreadHolder.clear();
    }

    private void writeError(HttpServletResponse response, String message) throws Exception {
        Result<String> error = ApiResult.error(message);
        response.setContentType("application/json;charset=UTF-8");
        Writer stream = response.getWriter();
        stream.write(JSONObject.toJSONString(error));
        stream.flush();
        stream.close();
    }
}
