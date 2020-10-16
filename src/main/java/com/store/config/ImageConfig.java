package com.store.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc
@Configuration
public class ImageConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/styles/**")
                .addResourceLocations("classpath:/styles/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/js/");


    }

}

