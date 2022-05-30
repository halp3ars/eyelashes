package com.bot.eyelashes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class EyelashesApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyelashesApplication.class, args);
    }

}
