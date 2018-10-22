package com.webshop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    @EnableWebMvc
    public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/webshopFront/**")
                    .addResourceLocations("file:/Users/Mateusz/Desktop/programing/web_shop/web_shop/src/main/resources/webshopFront/")
                    .setCachePeriod(31556926);
        }
    }

