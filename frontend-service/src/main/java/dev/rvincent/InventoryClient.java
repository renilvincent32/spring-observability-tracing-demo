package dev.rvincent;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class InventoryClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryClient.class);
    private final RestTemplate restTemplate;

    public InventoryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> ready(ObservationRegistry registry) {
        List<String> allItems = List.of("iPhone", "Laptop", "TV", "Watch");
        String userId = "1234";
        return applicationReadyEvent -> {
                    for (String item : allItems) {
                        Observation.createNotStarted("my.observation", registry)
                                .highCardinalityKeyValue(KeyValue.of("userId", userId))
                                .lowCardinalityKeyValue(KeyValue.of("itemName", item))
                                .observe(() -> {
                        LOGGER.info("Checking inventory for item: {}", item);
                        String response = restTemplate.getForObject("http://localhost:8081/inventory/{userId}/{item}", String.class, userId, item);
                        LOGGER.info("Item returned: {}", response);
                    });
                }
        };
    }

}
