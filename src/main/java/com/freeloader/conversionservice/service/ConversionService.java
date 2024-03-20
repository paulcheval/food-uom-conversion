package com.freeloader.conversionservice.service;

import java.util.List;
import java.util.Map;

import com.freeloader.conversionservice.exception.FoodConversionNotExistException;
import com.freeloader.conversionservice.exception.FoodsNotExistException;
import com.freeloader.conversionservice.model.ConversionRequest;
import com.freeloader.conversionservice.model.ConversionResponse;
import com.freeloader.conversionservice.model.FoodConversionRequest;
import com.freeloader.conversionservice.model.FoodConversionResponse;

public interface ConversionService {

	public ConversionResponse findDetailsForFoodAndQuantity(ConversionRequest request) throws  FoodConversionNotExistException;
	public List<String> findAllValidFoods() throws FoodsNotExistException;
	public List<String> findAllUomForFood(String food);
	public Map<String, List<String>> findAllFoodsWithUoms();
	public FoodConversionResponse createFoodConversion(FoodConversionRequest request) throws Exception;
	public void deleteFoodConversion(String food) throws Exception;
	
}
