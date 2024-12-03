package com.wirke.jobportal.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    
    private static final String UPLOAD_DIR = "photos";

    @Override
    public void addResourceHandlers(@SuppressWarnings("null") ResourceHandlerRegistry registry) {
        
        exposeDirectory(UPLOAD_DIR, registry);
    }
        
    private void exposeDirectory(String uploadDir, ResourceHandlerRegistry registry) {
        
        Path path = Paths.get(uploadDir);
        System.out.println("Exposing directory: " + path.toAbsolutePath());
        registry.addResourceHandler("/" + uploadDir + "/**")
                .addResourceLocations("file:" + path.toAbsolutePath() + "/");
    }
}
