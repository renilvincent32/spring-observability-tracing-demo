package dev.rvincent;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class InventoryService {

    private final ItemRepository itemRepository;
    private final static Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    private final ObservationRegistry observationRegistry;

    public InventoryService(ObservationRegistry observationRegistry, ItemRepository itemRepository) {
        this.observationRegistry = observationRegistry;
        this.itemRepository = itemRepository;
    }

    public String returnInventoryItem(String item, String userId) {
        return Observation
                .createNotStarted("check.item.availability", observationRegistry)
                .contextualName("check-item-availability")
                .highCardinalityKeyValue(KeyValue.of("userId", userId))
                .lowCardinalityKeyValue(KeyValue.of("itemName", item))
                .observe(() -> {
                    LOGGER.info("Retrieving item: {} for user: {}", item, userId);
                    return returnInventoryItemOrThrow(item);
                });
    }

    private String returnInventoryItemOrThrow(String item) {
        return itemRepository.findAll().stream()
                .map(Item::getName)
                .filter(i -> Objects.equals(i, item))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid item"));
    }
}
