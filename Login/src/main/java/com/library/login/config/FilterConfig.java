package com.library.login.config;

import com.library.login.filter.JwtAdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean jwtUserFilter() {

        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new JwtAdminFilter());
        filter.addUrlPatterns("/api/v1/all-user");

        return filter;
    }
}
