package com.ire.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.ire.app")
@SpringBootApplication
@EnableScheduling
public class App
{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
