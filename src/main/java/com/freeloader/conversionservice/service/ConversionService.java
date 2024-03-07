package com.freeloader.conversionservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;

import com.freeloader.conversionservice.UOM;
import com.freeloader.conversionservice.db.entities.FoodConversion;
import com.freeloader.conversionservice.db.repository.FoodConversionRepository;
import com.freeloader.conversionservice.model.ConversionRequest;
import com.freeloader.conversionservice.model.ConversionResponse;

@Service
public class ConversionService {

	private FoodConversionRepository repository;	
	
	public ConversionService(FoodConversionRepository repository) {
		this.repository = repository;
	}


	public ConversionResponse findDetailsForFoodAndQuantity(ConversionRequest request) {
		FoodConversion conversion = repository.findByFoodIgnoreCase(request.food());
		if (conversion != null) {
			return determineAmountBasedOnInputUnit(request, conversion);
		}
		return null;
	}
	
	public List<String> findAllValidFoods() {
		return repository.findDistinctFoods();
	}
	
	public List<String> findAllUomForFood(String food) {
		FoodConversion conversion = repository.findByFoodIgnoreCase(food);
		return determineValidUom(conversion);
	}
	
	public Map<String, List<String>> findAllFoodsWithUoms() {
		List<FoodConversion> allFoodConversions = new ArrayList<FoodConversion>();
		Map<String, List<String>> foodUoms = new HashMap<String, List<String>>();
		Iterable<FoodConversion> allFoodIterable = repository.findAll();
		
		allFoodIterable.forEach(food -> {
			allFoodConversions.add(food);
		});
		allFoodConversions.forEach(food -> {
			foodUoms.put(food.getFood(), determineValidUom(food));
		});
		return foodUoms;
		
	}
	
	private List<String> determineValidUom(FoodConversion conversion) {
		List<String> validUom = new ArrayList<String>();
		if (conversion.getCups() != null) { validUom.add(UOM.CUPS.getName()); }
		if (conversion.getGrams() != null) { validUom.add(UOM.GRAMS.getName()); }
		if (conversion.getOunces() != null) { validUom.add(UOM.OUNCES.getName()); }
		if (conversion.getTeaSpoons() != null) { validUom.add(UOM.TSPS.getName()); }
		if (conversion.getTableSpoons() != null) { validUom.add(UOM.TBSP.getName()); }
		return validUom; 
	}


	private ConversionResponse determineAmountBasedOnInputUnit(ConversionRequest request, FoodConversion conversion) {
		if (request.targetUnit().equalsIgnoreCase(UOM.GRAMS.getName())) {
			return new ConversionResponse(request.food(), request.fromUnit(),  request.fromAmount(), request.targetUnit(),
					request.fromAmount()*conversion.getGrams());
		}
		if (request.targetUnit().equalsIgnoreCase(UOM.OUNCES.getName())) {
			return new ConversionResponse( request.food(),request.fromUnit(), request.fromAmount(), request.targetUnit(),
					request.fromAmount()*conversion.getOunces());
		}
		if (request.targetUnit().equalsIgnoreCase(UOM.TSPS.getName())) {
			return new ConversionResponse( request.food(), request.fromUnit(), request.fromAmount(), request.targetUnit(),
					request.fromAmount()*conversion.getTeaSpoons());
		}
		if (request.targetUnit().equalsIgnoreCase(UOM.TBSP.getName())) {
			return new ConversionResponse( request.food(), request.fromUnit(), request.fromAmount(), request.targetUnit(),
					request.fromAmount()*conversion.getTableSpoons());
		}
		return new ConversionResponse( request.food(), request.fromUnit(), request.fromAmount(), request.targetUnit(),
				request.fromAmount()*conversion.getCups());
	}
}
