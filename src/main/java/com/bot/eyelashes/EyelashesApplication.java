package com.bot.eyelashes;

import com.bot.eyelashes.config.properties.TelegramProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TelegramProperties.class)
public class EyelashesApplication {
    public static void main(String[] args) {
        SpringApplication.run(EyelashesApplication.class, args);
    }

}