package cn.kmbeast.config;

import cn.kmbeast.Interceptor.RedisJwtInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RedisInterceptorConfig implements WebMvcConfigurer {

    @Value("${my-server.api-context-path}")
    private String api;

    @Resource
    private RedisJwtInterceptor redisJwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(redisJwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        api + "/user/login",
                        api + "/user/register",
                        api + "/file/upload",
                        api + "/file/getFile"
                );
    }
}
