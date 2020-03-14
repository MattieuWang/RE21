package com.re21.bouquiniste.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter()
    {
        return new HiddenHttpMethodFilter();
    }
}
