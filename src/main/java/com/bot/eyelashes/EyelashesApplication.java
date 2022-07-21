package com.bot.eyelashes;

import com.bot.eyelashes.config.properties.TelegramProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableConfigurationProperties(TelegramProperties.class)
public class EyelashesApplication {
    public static void main(String[] args) {
        SpringApplication.run(EyelashesApplication.class, args);
    }
}