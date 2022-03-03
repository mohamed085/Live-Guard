package com.liveguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin()
@SpringBootApplication
public class LiveguardApplication {

    public static void main(String[] args) {
        SpringApplication.run(LiveguardApplication.class, args);
    }

}
