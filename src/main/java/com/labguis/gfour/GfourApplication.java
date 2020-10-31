package com.labguis.gfour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.labguis")
@SpringBootApplication
public class GfourApplication {
    public static void main(String[] args) {
        SpringApplication.run(GfourApplication.class, args);
    }
}
