package com.freeloader.conversionservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.freeloader.conversionservice.model.ConversionRequest;
import com.freeloader.conversionservice.model.ConversionResponse;
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

}
