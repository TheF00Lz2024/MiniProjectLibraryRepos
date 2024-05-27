package com.library.webapp.config;

import com.library.webapp.filter.JwtUserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean jwtUserFilter() {

        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new JwtUserFilter());
        filter.addUrlPatterns("/api/v1/*");

        return filter;
    }

}
