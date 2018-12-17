package com.distributed.subproblem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SubproblemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubproblemApplication.class, args);
    }

}

