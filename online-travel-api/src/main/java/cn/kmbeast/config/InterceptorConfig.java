package cn.kmbeast.config;

import cn.kmbeast.Interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * API拦截器配置
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${my-server.api-context-path}")
    private String API;
    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器注册
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                // 放行登录、注册请求
                .excludePathPatterns(
                        API + "/user/login",
                        API + "/user/register",
                        API + "/file/upload",
                        API + "/file/getFile"
                );
    }
}
