package com.freeloader.conversionservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.freeloader.conversionservice.UOM;

import com.freeloader.conversionservice.db.entities.FoodConversion;
import com.freeloader.conversionservice.db.repository.FoodConversionRepository;
import com.freeloader.conversionservice.exception.FoodConversionAlreadyExistsException;
import com.freeloader.conversionservice.exception.FoodConversionNotExistException;
import com.freeloader.conversionservice.model.ConversionRequest;
import com.freeloader.conversionservice.model.ConversionResponse;
import com.freeloader.conversionservice.model.FoodConversionRequest;
import com.freeloader.conversionservice.model.FoodConversionResponse;

import jakarta.transaction.Transactional;

@Service
public class ConversionServiceImpl implements ConversionService {

	private FoodConversionRepository repository;

	public ConversionServiceImpl(FoodConversionRepository repository) {
		this.repository = repository;
	}

	@Override
	public ConversionResponse findDetailsForFoodAndQuantity(ConversionRequest request) {
		FoodConversion conversion = repository.findByFoodIgnoreCase(request.food());
		if (conversion != null) {
			return determineAmountBasedOnInputUnit(request, conversion);
		}
		return null;
	}

	@Override
	public List<String> findAllValidFoods() {
		return repository.findDistinctFoods();
	}

	@Override
	public List<String> findAllUomForFood(String food) {
		FoodConversion conversion = repository.findByFoodIgnoreCase(food);
		return determineValidUom(conversion);
	}

	@Override
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

	@Override
	public FoodConversionResponse createFoodConversion(FoodConversionRequest request) throws Exception {
		 if (repository.existsByFood(request.food())) {
			  throw new FoodConversionAlreadyExistsException(); } return
			  convertToFoodCOnversionResponse(repository.save(buildFoodConversionEntity(
			  request))); 
	}

	@Override
	public void deleteFoodConversion(String food) throws Exception {
		if (!repository.existsByFood(food)) { throw new
			  FoodConversionNotExistException(); } repository.deleteByFood(food);
		
	}


	private FoodConversionResponse convertToFoodCOnversionResponse(FoodConversion entity) {
		return new FoodConversionResponse(entity.getFood(), entity.getTeaSpoons(), entity.getTableSpoons(),
				entity.getCups(), entity.getGrams(), entity.getOunces());
	}

	private FoodConversion buildFoodConversionEntity(FoodConversionRequest request) {
		return new FoodConversion(request.food(), request.tsp(), request.tbsp(), request.cups(), request.grams(),
				request.ounces());
	}

	private List<String> determineValidUom(FoodConversion conversion) {
		List<String> validUom = new ArrayList<String>();
		if (conversion.getCups() != null) {
			validUom.add(UOM.CUPS.getName());
		}
		if (conversion.getGrams() != null) {
			validUom.add(UOM.GRAMS.getName());
		}
		if (conversion.getOunces() != null) {
			validUom.add(UOM.OUNCES.getName());
		}
		if (conversion.getTeaSpoons() != null) {
			validUom.add(UOM.TSPS.getName());
		}
		if (conversion.getTableSpoons() != null) {
			validUom.add(UOM.TBSP.getName());
		}
		return validUom;
	}

	private ConversionResponse determineAmountBasedOnInputUnit(ConversionRequest request, FoodConversion conversion) {
		if (request.targetUnit().equalsIgnoreCase(UOM.GRAMS.getName())) {
			return new ConversionResponse(request.food(), request.fromUnit(), request.fromAmount(),
					request.targetUnit(), request.fromAmount() * conversion.getGrams());
		}
		if (request.targetUnit().equalsIgnoreCase(UOM.OUNCES.getName())) {
			return new ConversionResponse(request.food(), request.fromUnit(), request.fromAmount(),
					request.targetUnit(), request.fromAmount() * conversion.getOunces());
		}
		if (request.targetUnit().equalsIgnoreCase(UOM.TSPS.getName())) {
			return new ConversionResponse(request.food(), request.fromUnit(), request.fromAmount(),
					request.targetUnit(), request.fromAmount() * conversion.getTeaSpoons());
		}
		if (request.targetUnit().equalsIgnoreCase(UOM.TBSP.getName())) {
			return new ConversionResponse(request.food(), request.fromUnit(), request.fromAmount(),
					request.targetUnit(), request.fromAmount() * conversion.getTableSpoons());
		}
		return new ConversionResponse(request.food(), request.fromUnit(), request.fromAmount(), request.targetUnit(),
				request.fromAmount() * conversion.getCups());
	}


	
	
}
