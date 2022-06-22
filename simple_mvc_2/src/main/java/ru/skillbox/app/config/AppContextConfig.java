package ru.skillbox.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.skillbox.app.services.IdProvider;

@Configuration
@ComponentScan(basePackages = "ru.skillbox.app")
public class AppContextConfig {

    @Bean
    public IdProvider idProvider() {
        return new IdProvider();
    }
}
