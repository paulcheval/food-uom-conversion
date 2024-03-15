package com.freeloader.conversionservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.freeloader.conversionservice.service.ConversionService;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConversionControllerImplTest {
	
	private static final List<String> VALID_FOODS_LIST = Arrays.asList("Flour", "Sauce", "Oil"); 
	
	@MockBean
	private ConversionService service;
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void ensureAllFoodsReturnsListofValidFoodsWhenNoError() { 
		when(service.findAllValidFoods()).thenReturn(VALID_FOODS_LIST);	
		ResponseEntity<List<String>> responseEntity = restTemplate.exchange(
				"/foods", 
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
		when(service.findAllValidFoods()).thenReturn(new ArrayList<String>());	
		ResponseEntity<List<String>> responseEntity = restTemplate.exchange(
				"/foods", 
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<List<String>>() {} );
				
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
 
	}

	@Test
	void ensureAllFoodsReturnsErrorWhenNullValues() { 
		when(service.findAllValidFoods()).thenReturn(null);	
		ResponseEntity<List<String>> responseEntity = restTemplate.exchange(
				"/foods", 
				HttpMethod.GET, 
				null, 
				new ParameterizedTypeReference<List<String>>() {} );
				
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
 
	}
}
