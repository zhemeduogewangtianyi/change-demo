package com.wty.changedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.wty.changedemo"})
public class ChangeDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChangeDemoApplication.class, args);
    }

}
