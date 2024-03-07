package com.freeloader.conversionservice.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.freeloader.conversionservice.FoodConversionServiceApplication;
import com.freeloader.conversionservice.model.ConversionRequest;
import com.freeloader.conversionservice.model.ConversionResponse;
import com.freeloader.conversionservice.model.UomRequest;
import com.freeloader.conversionservice.service.ConversionService;

@RestController
public class ConversionControllerImpl implements ConversionController {
	private static final Logger log = LoggerFactory.getLogger(ConversionControllerImpl.class);

	private ConversionService service;
	

	public ConversionControllerImpl(ConversionService service) {
		super();
		this.service = service;
	}


	@Override

	public  ResponseEntity<ConversionResponse> determineConversionValues(@RequestBody ConversionRequest request) {
		log.info("Conversion Being called with" + request.toString());
		ConversionResponse response = service.findDetailsForFoodAndQuantity(request);
		if (response == null) {
			return  new ResponseEntity<ConversionResponse>(HttpStatus.NOT_FOUND);
		}
		log.info("The calculated conversion response: " + response.toString());
		return new ResponseEntity<ConversionResponse>(response, HttpStatus.OK);
		
	}


	@Override
	public ResponseEntity<List<String>> retrieveValidFoods() {
		log.info("Got message to retrieve available foods");
		List<String> allValidFoods = service.findAllValidFoods();
		log.info("Found available foods of: " + allValidFoods.toString() );
				
		if (allValidFoods.isEmpty()) {
			return  new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<String>>(allValidFoods, HttpStatus.OK); 
	}


	@Override
	public ResponseEntity<List<String>>  retrieveValidUom(@PathVariable String foodId) {	
		log.info("Got message to get all avilable uom for: " + foodId);
		List<String> uoms = service.findAllUomForFood(foodId);
		log.info("Found uoms foods of: " + uoms.toString() );
		if (uoms.isEmpty()) {
			return  new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<List<String>>(uoms, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<Map<String, List<String>>> retrieveAllAvailableFoodsUom() {
		log.info("Got message to get all avilable foods and uom ");
		Map<String, List<String>> allFoodsWithUoms = service.findAllFoodsWithUoms();
		log.info("Found all foods with uoms: " + allFoodsWithUoms.toString() );

		if (allFoodsWithUoms.isEmpty()) {
			return  new ResponseEntity<Map<String, List<String>>>(HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<Map<String, List<String>>>(allFoodsWithUoms, HttpStatus.OK);
	}



}
