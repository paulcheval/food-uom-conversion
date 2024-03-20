package com.freeloader.conversionservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.freeloader.conversionservice.UOM;
import com.freeloader.conversionservice.conversion.FoodConversionConverter;
import com.freeloader.conversionservice.db.entities.FoodConversion;
import com.freeloader.conversionservice.db.repository.FoodConversionRepository;
import com.freeloader.conversionservice.exception.FoodConversionAlreadyExistsException;
import com.freeloader.conversionservice.exception.FoodConversionNotExistException;
import com.freeloader.conversionservice.exception.FoodsNotExistException;
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
	public ConversionResponse findDetailsForFoodAndQuantity(ConversionRequest request) throws  FoodConversionNotExistException {
		Optional<FoodConversion> conversion = Optional.ofNullable(repository.findByFoodIgnoreCase(request.food()));		
		if (conversion.isPresent()) {
			return FoodConversionConverter.determineAmountBasedOnInputUnit(request, conversion.get());
		}
		
		throw new FoodConversionNotExistException();
	}

	@Override
	public List<String> findAllValidFoods() throws FoodsNotExistException{
		Optional<List<String>> distinctFoods = Optional.ofNullable(repository.findDistinctFoods());
		if (distinctFoods.isPresent()) {
			return distinctFoods.get();
		}
		throw new FoodsNotExistException();
	}

	@Override
	public List<String> findAllUomForFood(String food) {
		if (food == null) { return new ArrayList<String>(); }
		if (food.isEmpty()) { return new ArrayList<String>(); }
		return FoodConversionConverter.determineValidUom(repository.findByFoodIgnoreCase(food));
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
			foodUoms.put(food.getFood(), FoodConversionConverter.determineValidUom(food));
		});
		return foodUoms;
	}

	@Override
	public FoodConversionResponse createFoodConversion(FoodConversionRequest request) throws Exception {
		 if (repository.existsByFood(request.food())) {
			  throw new FoodConversionAlreadyExistsException(); } return
			  convertToFoodConversionResponse(repository.save(buildFoodConversionEntity(
			  request))); 
	}

	@Override
	@Transactional
	public void deleteFoodConversion(String food) throws Exception {
		if (!repository.existsByFood(food)) { throw new
			  FoodConversionNotExistException(); } repository.deleteByFood(food);
		
	}


	private FoodConversionResponse convertToFoodConversionResponse(FoodConversion entity) {
		return new FoodConversionResponse(entity.getFood(), entity.getTeaSpoons(), entity.getTableSpoons(),
				entity.getCups(), entity.getGrams(), entity.getOunces());
	}

	private FoodConversion buildFoodConversionEntity(FoodConversionRequest request) {
		return new FoodConversion(request.food(), request.tsp(), request.tbsp(), request.cups(), request.grams(),
				request.ounces());
	}
		
	
}
