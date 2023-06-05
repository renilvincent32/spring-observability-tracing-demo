package dev.rvincent;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ItemRepository itemRepository) {
		return args -> List.of("iPhone", "Laptop", "TV")
				.forEach(item -> itemRepository.save(new Item().setName(item)));
	}
}
