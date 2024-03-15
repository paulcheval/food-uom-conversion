package com.freeloader.conversionservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.freeloader.conversionservice.db.entities.FoodConversion;
import com.freeloader.conversionservice.db.repository.FoodConversionRepository;

@SpringBootApplication
public class FoodConversionServiceApplication {
	
	private static final Logger log = LoggerFactory.getLogger(FoodConversionServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FoodConversionServiceApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(FoodConversionRepository repository) {
		return (args) -> {
			
			
			  repository.save(new FoodConversion("Flour", null, null, 1.0, 120.0, 4.25));
			  repository.save(new FoodConversion("Apple Sauce", null, null,1.0, 255.0,
			  9.0)); 
			  repository.save(new FoodConversion("Grated Carrots", null, null,1.0,
			  99.0, 3.5)); 
			  repository.save(new FoodConversion("Tomato Paste", null, 1.0,
			  null, 29.0, 1.0)); 
			  repository.save(new FoodConversion("Baking Powder", 1.0,
			  null, null, 4.0, null)); 
			  repository.save(new FoodConversion("Baking Soda",
			  1.0, null, null, 6.0, null));
			 
			 
			 
			  log.info("Conversions loaded are"); 
			  repository.findAll().forEach(conversion -> {
				  log.info(conversion.toString()); 
			  });
			 /* 
			 * log.info("Find 1st one"); FoodConversion firstOne = repository.findById(1L);
			 * log.info(firstOne.toString());
			 * 
			 * log.info("Find Flour"); log.info(" flour: " +
			 * repository.findByFoodIgnoreCase("Flour").toString());
			 * 
			 * log.info("Find Grated Carrots"); log.info(" Grated Carrots: " +
			 * repository.findByFoodIgnoreCase("Grated Carrots").toString());
			 */
		};
		
	}
	

}
