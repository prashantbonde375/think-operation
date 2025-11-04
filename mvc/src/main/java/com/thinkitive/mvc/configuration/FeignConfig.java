package com.thinkitive.mvc.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (attrs != null) {
                    HttpServletRequest request = attrs.getRequest();
                    Object token = request.getSession().getAttribute("jwt");

                    if (token != null) {
                        template.header("Authorization", "Bearer " + token);
                        System.out.println("✅ Feign added Authorization header: Bearer " + token);
                    } else {
                        System.out.println("⚠️ JWT not found in session when Feign called");
                    }
                } else {
                    System.out.println("⚠️ No RequestContext found (Feign called outside HTTP request)");
                }

            }
        };
    }
}
