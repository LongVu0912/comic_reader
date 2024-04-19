package com.api.comic_reader.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(@SuppressWarnings("null") ResourceHandlerRegistry registry) {
        String path = "file:///C:/Users/LongVu/Desktop/comic_reader/src/main/resources/public/comics/";
        registry.addResourceHandler("/resource/comic/**")
                .addResourceLocations(path);
    }
}
