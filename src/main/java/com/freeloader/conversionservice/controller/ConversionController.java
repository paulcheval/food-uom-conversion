package com.freeloader.conversionservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freeloader.conversionservice.model.ConversionRequest;
import com.freeloader.conversionservice.model.ConversionResponse;
import com.freeloader.conversionservice.model.FoodConversionRequest;
import com.freeloader.conversionservice.model.FoodConversionResponse;
import com.freeloader.conversionservice.model.UomRequest;


public interface ConversionController {
	
	@PostMapping("/convert")
	public ResponseEntity<ConversionResponse> determineConversionValues(ConversionRequest request);
	
	@GetMapping("/foods")
	public ResponseEntity<List<String>> retrieveValidFoods();
	
	@GetMapping("/foods/{foodId}/uom")
	public ResponseEntity<List<String>>  retrieveValidUom(String food);
	
	@GetMapping("/foods/uom")
	public ResponseEntity<Map<String, List<String>>>  retrieveAllAvailableFoodsUom();

	@PutMapping("/foods")
	public ResponseEntity<FoodConversionResponse> saveFoodConversion(FoodConversionRequest request);
	
	@DeleteMapping("/foods")
	public ResponseEntity<String> deleteFoodConversion(String food);
}
