package cn.jimyag.zizhuxingserver.Utils;

import cn.jimyag.zizhuxingserver.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//// 跨域配置类
//@Configuration
//public class WebConfig extends WebMvcConfigurerAdapter {
//
//    /**
//     * 域访问的路径
//     * 请求资源
//     * 方法
//     * 允许携带
//     * 最大时间
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("Http://localhost:8080", "null")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
////                .allowCredentials(true)
//                .maxAge(3600);
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authenticationInterceptor())
//                .addPathPatterns("/**");    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
//    }
//
//    @Bean
//    public AuthenticationInterceptor authenticationInterceptor() {
//        return new AuthenticationInterceptor();
//    }
//}
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
    }

    /**
     * 域访问的路径
     * 请求资源
     * 方法
     * 允许携带
     * 最大时间
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .allowedHeaders("*")
                .maxAge(3600);
        ;
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
}
