package com.freeloader.conversionservice.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.freeloader.conversionservice.db.entities.FoodConversion;

public interface FoodConversionRepository extends CrudRepository<FoodConversion, Long> {
	
	FoodConversion findByFoodIgnoreCase(String food);
	
	FoodConversion findById(long id);
	
	@Query("SELECT DISTINCT food from FoodConversion")
	List<String> findDistinctFoods();
		
	
}
