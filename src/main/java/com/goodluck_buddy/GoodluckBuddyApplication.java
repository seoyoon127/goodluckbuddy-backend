package com.goodluck_buddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GoodluckBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodluckBuddyApplication.class, args);
    }

}
