package com.freeloader.conversionservice.sd.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import com.freeloader.conversionservice.db.entities.FoodConversion;
import com.freeloader.conversionservice.db.repository.FoodConversionRepository;

@Component
public class FoodConversionLoader implements SmartInitializingSingleton {
	
	private static final Logger log = LoggerFactory.getLogger(FoodConversionLoader.class);

	private final FoodConversionRepository repository;
	
	public FoodConversionLoader(FoodConversionRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void afterSingletonsInstantiated() {
		repository.save(new FoodConversion("Flour", null, null, 1.0, 120.0, 4.25));
		repository.save(new FoodConversion("Apple Sauce", null, null, 1.0, 255.0, 9.0));
		repository.save(new FoodConversion("Grated Carrots", null, null, 1.0, 99.0, 3.5));
		repository.save(new FoodConversion("Tomato Paste", null, 1.0, null, 29.0, 1.0));
		repository.save(new FoodConversion("Baking Powder", 1.0, null, null, 4.0, null));
		repository.save(new FoodConversion("Baking Soda", 1.0, null, null, 6.0, null));

		log.info("Conversions loaded are");
		repository.findAll().forEach(conversion -> {
			log.info(conversion.toString());
		});
	}

}
