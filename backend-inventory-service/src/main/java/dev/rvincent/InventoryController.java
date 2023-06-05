package dev.rvincent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("{userId}/{item}")
    public String returnInventoryItem(@PathVariable String userId, @PathVariable String item) {
        LOGGER.info("Checking inventory for item: {}", item);
        return inventoryService.returnInventoryItem(item, userId);
    }
}
