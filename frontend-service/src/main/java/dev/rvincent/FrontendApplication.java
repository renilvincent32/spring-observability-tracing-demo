package dev.rvincent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class FrontendApplication {


    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder, ResponseErrorHandler errorHandler) {
        return builder.errorHandler(errorHandler).build();
    }
}
