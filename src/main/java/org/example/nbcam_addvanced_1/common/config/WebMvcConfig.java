package org.example.nbcam_addvanced_1.common.config;

import lombok.RequiredArgsConstructor;
import org.example.nbcam_addvanced_1.common.interceptor.UserOwnerCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserOwnerCheckInterceptor userOwnerCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userOwnerCheckInterceptor)
            .addPathPatterns("/api/user/**/email");
    }
}