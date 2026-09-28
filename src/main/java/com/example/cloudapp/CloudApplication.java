package com.example.cloudapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
public class CloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudApplication.class, args);
    }

    @RestController
    static class Controller {

        @GetMapping("/")
        public Map<String, String> home() {
            return Map.of(
                "application", "three-cloud-java",
                "message", "Hello from Jenkins"
            );
        }

        @GetMapping("/api/hello")
        public Map<String, String> hello() {
            return Map.of(
                "message", "Hello from three-cloud deployment"
            );
        }
    }
}
