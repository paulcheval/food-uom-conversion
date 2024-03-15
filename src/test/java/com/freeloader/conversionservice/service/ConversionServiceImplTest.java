package com.freeloader.conversionservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.freeloader.conversionservice.UOM;
import com.freeloader.conversionservice.db.entities.FoodConversion;
import com.freeloader.conversionservice.db.repository.FoodConversionRepository;
import com.freeloader.conversionservice.model.ConversionRequest;
import com.freeloader.conversionservice.model.ConversionResponse;
import com.freeloader.conversionservice.model.FoodConversionRequest;

@SpringBootTest
class ConversionServiceImplTest {
	
	private static final String BAD_FOOD_NAME = "Bad";
	private static final String FLOUR_FOOD_NAME = "Flour";
	
	private static final ConversionRequest BAD_REQUEST = new ConversionRequest(BAD_FOOD_NAME, UOM.CUPS.getName(), 1.0, UOM.GRAMS.getName());
	private static final ConversionRequest FLOUR_REQUEST = new ConversionRequest(FLOUR_FOOD_NAME, UOM.CUPS.getName(), 1.0, UOM.GRAMS.getName());
	private static final ConversionResponse FLOUR_RESPONSE = new ConversionResponse(FLOUR_FOOD_NAME, UOM.CUPS.getName(), 1.0, UOM.GRAMS.getName(), 120);
	
	private static final FoodConversion FLOUR_CONVERSION = new FoodConversion(FLOUR_FOOD_NAME, 1.0, 120.0, 6.0);
	
	private static final List<String> DISTINCT_FOODS = Arrays.asList("Flour", "Sauce", "Oil");
	private static final List<String> UOM_CUPS_GRAM_OUNCES = Arrays.asList(UOM.CUPS.getName(),UOM.GRAMS.getName(), UOM.OUNCES.getName());
	
	@MockBean
	private FoodConversionRepository mockRepo;
	
	@Autowired
	ConversionServiceImpl service;

	@Test
	void ensureNullFindByFoodIgnoreCaseWhenFoodNotInDB() {
		when(mockRepo.findByFoodIgnoreCase(BAD_FOOD_NAME)).thenReturn(null);		
		assertNull(service.findDetailsForFoodAndQuantity(BAD_REQUEST));
	}
	
	@Test
	void ensureConversionResponseWHenFindByFoodIgnoreCaseWhenFoodInDB() {
		when(mockRepo.findByFoodIgnoreCase(FLOUR_FOOD_NAME)).thenReturn(FLOUR_CONVERSION);		
		assertTrue(FLOUR_RESPONSE.equals(service.findDetailsForFoodAndQuantity(FLOUR_REQUEST)));
	}
	
	@Test
	void ensureNullfindAllValidFoodsWhenFoodNotInDB() {
		when(mockRepo.findByFoodIgnoreCase(BAD_FOOD_NAME)).thenReturn(null);		
		assertNull(service.findDetailsForFoodAndQuantity(BAD_REQUEST));
	}
	
	@Test
	void ensureConversionResponsefindAllValidFoodsWhenFoodsInDB() {
		when(mockRepo.findDistinctFoods()).thenReturn(DISTINCT_FOODS);	
		List<String> response = service.findAllValidFoods();
		assertEquals(DISTINCT_FOODS.size(), response.size());
		assertTrue(response.containsAll(DISTINCT_FOODS));		
	}
	
	@Test
	void ensureFindAllUomForFoodReturnsEmptyListIfNoInput() {
		assertEquals(new ArrayList<String>(), service.findAllUomForFood(null));
	}
	
	@Test
	void ensureFindAllUomForFoodReturnsEmptyListIfBlankInput() {
		assertEquals(new ArrayList<String>(), service.findAllUomForFood(""));
	}
	
	@Test
	void ensureFindAllUomForFoodReturnsListIfValidInput() {
		when(mockRepo.findByFoodIgnoreCase(FLOUR_FOOD_NAME)).thenReturn(FLOUR_CONVERSION);
		List<String> response = service.findAllUomForFood(FLOUR_FOOD_NAME);
		assertEquals(UOM_CUPS_GRAM_OUNCES.size(), response.size());
		assertTrue(service.findAllUomForFood(FLOUR_FOOD_NAME).containsAll(UOM_CUPS_GRAM_OUNCES));
	}

}
