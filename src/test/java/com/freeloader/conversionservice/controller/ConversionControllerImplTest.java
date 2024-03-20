package com.freeloader.conversionservice.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.freeloader.conversionservice.UOM;
import com.freeloader.conversionservice.exception.FoodConversionNotExistException;
import com.freeloader.conversionservice.exception.FoodsNotExistException;
import com.freeloader.conversionservice.model.FoodConversionRequest;
import com.freeloader.conversionservice.model.FoodConversionResponse;
import com.freeloader.conversionservice.model.UomResponse;
import com.freeloader.conversionservice.service.ConversionService;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest
//@AutoConfigureMockMvc
class ConversionControllerImplTest {
	
	private static final List<String> VALID_FOODS_LIST = Arrays.asList("Flour", "Sauce", "Oil"); 
	private static final List<String> VALID_UOM_FOR_FLOUR = Arrays.asList(UOM.CUPS.getName(), UOM.GRAMS.getName(), UOM.OUNCES.getName()); 
	
	@MockBean
	private ConversionService service;
	//@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void ensureAllFoodsReturnsListofValidFoodsWhenNoError() { 
		when(service.findAllValidFoods()).thenReturn(VALID_FOODS_LIST);	
		ResponseEntity<List<String>> responseEntity = restTemplate.exchange(
				"/food-conversion/foods", 
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<List<String>>() {} );
		
		List<String> responseBody = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(VALID_FOODS_LIST.size(), responseBody.size());   
        assertTrue(responseBody.containsAll(VALID_FOODS_LIST));   
	}
	
	@Test
	void ensureAllFoodsReturnsErrorWhenNoFoodsFound() { 
		when(service.findAllValidFoods()).thenThrow(new FoodsNotExistException());
		ResponseEntity<List<String>> responseEntity = restTemplate.exchange(
				"/food-conversion/foods", 
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<List<String>>() {} );
				
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
 
	}

	@Test
	void ensureAllFoodsReturnsErrorWhenNullValues() { 
		when(service.findAllValidFoods()).thenThrow(new FoodConversionNotExistException());	
		ResponseEntity<List<String>> responseEntity = restTemplate.exchange(
				"/food-conversion/foods", 
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<List<String>>() {} );
				
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
 
	}
	
	@Test
	void ensureSavingOfNewFoodCOnversionIsSuccessful() throws Exception { 
		FoodConversionRequest request = new FoodConversionRequest("Oat Bran", null, null, 1.0, 100.0, 1.5);
		FoodConversionResponse response = new FoodConversionResponse("Oat Bran", null, null, 1.0, 100.0, 1.5);
		
		when(service.createFoodConversion(any(FoodConversionRequest.class))).thenReturn(response);	
				
		ResponseEntity<FoodConversionResponse> responseEntity = restTemplate.exchange(
				"/food-conversion/foods", 
				HttpMethod.PUT, 
				new HttpEntity<FoodConversionRequest>(request), 
				new ParameterizedTypeReference<FoodConversionResponse>() {} );
				
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
 
	}
		
}
