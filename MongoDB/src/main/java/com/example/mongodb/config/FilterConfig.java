package com.example.mongodb.config;

import com.example.mongodb.filter.JwtRolesFilter;
import com.example.mongodb.filter.JwtUserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean jwtUserFilter() {

        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new JwtUserFilter());
        filter.addUrlPatterns("/api/v1/book/*");

        return filter;
    }
    @Bean
    public FilterRegistrationBean jwtRolesFilter() {

        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new JwtRolesFilter());
        filter.addUrlPatterns("/api/v1/author/*");

        return filter;
    }
}
