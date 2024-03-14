package com.freeloader.conversionservice.db.repository;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import com.freeloader.conversionservice.db.entities.FoodConversion;

@DataJpaTest
public class FoodConversionRepositoryTest {
	
	private static final List<String> expectedFood = asList("Flour", "Sauce", "Sugar");
	private static final FoodConversion SUGAR = new FoodConversion("Sugar", 10.0, 30.0, 1.0, 125.0, 5.0);
	private static final FoodConversion FLOUR = new FoodConversion("Flour", 1, 120, 2);
	

	@BeforeEach
	public  void setup() {		
		em.persist(new FoodConversion("Flour", 1, 120, 2));
		em.persist(new FoodConversion("Sauce", 2, 20, 3));
		em.persist(new FoodConversion("Sugar", 10.0, 30.0, 1.0, 125.0, 5.0));
	}
	
	@Autowired
	private  TestEntityManager em;
	
	@Autowired
	private FoodConversionRepository repository;

	@Test
	void ensureFinFoodByIgnoredCaseFindsDataWhenNotMatchingCase() {
		FoodConversion byFoodIgnoreCase = repository.findByFoodIgnoreCase("SUGAR");
		assertTrue(byFoodIgnoreCase.equals(SUGAR));		
	}

	@Test
	void ensureFindByIdFindsWhenIdMatches() {
		FoodConversion food = repository.findByFoodIgnoreCase("SUGAR");
		
		Optional<FoodConversion> foodConversion = repository.findById(food.getId());
		if (foodConversion.isPresent()) {
			assertTrue(foodConversion.get().equals(SUGAR));
		} else {
			fail("findById did not return value");
		}
		
		
	}
	
	@Test
	void ensureFindByIdNotFindWhenIdNotMatch() {
		FoodConversion foodConversion = repository.findById(11);
		assertNull(foodConversion);
	}

	@Test
	void ensureFindDistinctFoodsFindsAllDisctictFoods() {		
		List<String> distinctFoods = repository.findDistinctFoods();
		assertEquals(3, distinctFoods.size());
		assertTrue(distinctFoods.containsAll(expectedFood));
	}

	@Test
	void ensureExistsByFoodReturnsCorrectly() {
		assertTrue(repository.existsByFood("Flour"));
		assertFalse(repository.existsByFood("Gravy"));
	}

	@Test
	void enusreDeleteByFoodRemovesDataFromDatabase() {
		List<FoodConversion> allFoodConversions = new ArrayList<FoodConversion>();
		Iterable<FoodConversion> all = repository.findAll();
		all.forEach(food -> {
			allFoodConversions.add(food);
		});
		assertEquals(3, allFoodConversions.size());
		assertTrue(allFoodConversions.contains(FLOUR));
		
		repository.deleteByFood("Flour");
		
		
		Iterable<FoodConversion> updatedAll = repository.findAll();
		List<FoodConversion> updatedAllFoodConversions = new ArrayList<FoodConversion>();
		updatedAll.forEach(food -> {
			updatedAllFoodConversions.add(food);
		});
		
		assertEquals(2, updatedAllFoodConversions.size());
		assertFalse(updatedAllFoodConversions.contains(FLOUR));
	}

}
