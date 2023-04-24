package com.server.watermelonserverv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WatermelonServerV1Application {

    public static void main(String[] args) {
        SpringApplication.run(WatermelonServerV1Application.class, args);
    }

}
