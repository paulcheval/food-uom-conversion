package com.freeloader.conversionservice.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.freeloader.conversionservice.exception.FoodConversionAlreadyExistsException;
import com.freeloader.conversionservice.exception.FoodConversionNotExistException;
import com.freeloader.conversionservice.exception.FoodsNotExistException;
import com.freeloader.conversionservice.exception.UnknownConversionExcpetion;
import com.freeloader.conversionservice.model.ConversionRequest;
import com.freeloader.conversionservice.model.ConversionResponse;
import com.freeloader.conversionservice.model.FoodConversionRequest;
import com.freeloader.conversionservice.model.FoodConversionResponse;
import com.freeloader.conversionservice.model.UomResponse;
import com.freeloader.conversionservice.service.ConversionService;

@RestController
@CrossOrigin
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
		ConversionResponse response;
		try {
			response = service.findDetailsForFoodAndQuantity(request);
		} catch (FoodConversionNotExistException e) {
			return  new ResponseEntity<ConversionResponse>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return  new ResponseEntity<ConversionResponse>(HttpStatus.NOT_FOUND);
		}
		log.info("The calculated conversion response: " + response.toString());
		return new ResponseEntity<ConversionResponse>(response, HttpStatus.OK);		
	}


	@Override
	public ResponseEntity<List<String>> retrieveValidFoods() {
		log.info("Got message to retrieve available foods");
		List<String> allValidFoods;
		try {
			allValidFoods = service.findAllValidFoods();
		} catch (FoodsNotExistException e) {
			return  new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return  new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
		}
		log.info("Found available foods of: " + allValidFoods.toString() );						
		return new ResponseEntity<List<String>>(allValidFoods, HttpStatus.OK); 
	}


	@Override
	public ResponseEntity<UomResponse>  retrieveValidUom(@PathVariable String foodId) {	
		log.info("Got message to get all avilable uom for: " + foodId);
		UomResponse uoms = new UomResponse(service.findAllUomForFood(foodId));
		log.info("Found uoms foods of: " + uoms.toString() );
		if (uoms.uoms().isEmpty()) {
			return  new ResponseEntity<UomResponse>(HttpStatus.NOT_FOUND); 
		}
		return new ResponseEntity<UomResponse>(uoms, HttpStatus.OK);
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


	@Override
	public ResponseEntity<FoodConversionResponse> saveFoodConversion(@RequestBody FoodConversionRequest request) {
		log.info("Got message to add a new food conversion: " + request.toString());
		FoodConversionResponse response;
		try {
			response = service.createFoodConversion(request);
		} catch (FoodConversionAlreadyExistsException e) {
			log.info("The save did not work since the items already exists");
			throw new FoodConversionAlreadyExistsException();
		} catch (Exception e) {
			log.info("Something went wrong saving");
			throw new UnknownConversionExcpetion();
		}
		
						
		log.info("The save generated: " + response.toString() );
		return new ResponseEntity<FoodConversionResponse>(response, HttpStatus.CREATED);

	}


	@Override
	public ResponseEntity<String> deleteFoodConversion(@RequestParam String food) {
		log.info("Got message to delete a food conversion: " + food);
		
		try {
			service.deleteFoodConversion(food);
		} catch (FoodConversionNotExistException e) {
			log.info("The delete did not work since the items already exists");
			throw new FoodConversionAlreadyExistsException();
		} catch (Exception e) {
			log.info("Something went wrong deleting" + e.toString());
			throw new UnknownConversionExcpetion();
		}
		
						
		log.info("The delete finsihed" );
		return new ResponseEntity<String>(HttpStatus.OK);
	}



}
