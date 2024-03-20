package com.freeloader.conversionservice.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.freeloader.conversionservice.db.entities.FoodConversion;

@Repository
public interface FoodConversionRepository extends CrudRepository<FoodConversion, Long> {
	
	FoodConversion findByFoodIgnoreCase(String food);
	
	FoodConversion findById(long id);
	
	@Query("SELECT DISTINCT food from FoodConversion")
	List<String> findDistinctFoods();
	
	boolean existsByFood(String food);
	
	void deleteByFood(String food);
		
	
}
