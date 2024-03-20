package com.freeloader.conversionservice.conversion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.freeloader.conversionservice.UOM;
import com.freeloader.conversionservice.db.entities.FoodConversion;
import com.freeloader.conversionservice.model.ConversionRequest;
import com.freeloader.conversionservice.model.ConversionResponse;



public class FoodConversionConverter {

	public static ConversionResponse determineAmountBasedOnInputUnit(ConversionRequest request,
			FoodConversion conversion) {
		if (request == null) { return null; }
		if (conversion == null) { return null; }
		
		if (request.targetUnit().equalsIgnoreCase(UOM.GRAMS.getName())) {
			return buildConversionResponse(request, conversion, () -> conversion.getGrams());
		} else if (request.targetUnit().equalsIgnoreCase(UOM.OUNCES.getName())) {
			return buildConversionResponse(request, conversion, () -> conversion.getOunces());
		} else if (request.targetUnit().equalsIgnoreCase(UOM.TBSP.getName())) {
			return buildConversionResponse(request, conversion, () -> conversion.getTableSpoons());
		} else if (request.targetUnit().equalsIgnoreCase(UOM.TSPS.getName())) {
			return buildConversionResponse(request, conversion, () -> conversion.getTeaSpoons());
		} else if (request.targetUnit().equalsIgnoreCase(UOM.CUPS.getName())) {
			return buildConversionResponse(request, conversion, () -> conversion.getCups());
		}
		return null;
	}

	private static ConversionResponse buildConversionResponse(ConversionRequest request, FoodConversion conversion, Supplier<Double> method) {
		if (request.fromUnit().equalsIgnoreCase(UOM.CUPS.getName())) {
			return new ConversionResponse(request.food(), request.fromUnit(), request.fromAmount(),
					request.targetUnit(), request.fromAmount() * method.get()/conversion.getCups());
		} else if (request.fromUnit().equalsIgnoreCase(UOM.TSPS.getName())) {
			return new ConversionResponse(request.food(), request.fromUnit(), request.fromAmount(),
					request.targetUnit(), request.fromAmount() * method.get()/conversion.getTeaSpoons());
		} else if (request.fromUnit().equalsIgnoreCase(UOM.TBSP.getName())) {
			return new ConversionResponse(request.food(), request.fromUnit(), request.fromAmount(),
					request.targetUnit(), request.fromAmount() * method.get()/conversion.getTableSpoons());
		} else if (request.fromUnit().equalsIgnoreCase(UOM.OUNCES.getName())) {
			return new ConversionResponse(request.food(), request.fromUnit(), request.fromAmount(),
					request.targetUnit(), request.fromAmount() * method.get()/conversion.getOunces());
		} else {
			return new ConversionResponse(request.food(), request.fromUnit(), request.fromAmount(),
					request.targetUnit(), request.fromAmount() * method.get()/conversion.getGrams());
		}
	}

	public static List<String> determineValidUom(FoodConversion conversion) {
		List<String> validUom = new ArrayList<String>();
		if (conversion.getCups() != null) { validUom.add(UOM.CUPS.getName()); }
		if (conversion.getGrams() != null) { validUom.add(UOM.GRAMS.getName()); }
		if (conversion.getOunces() != null) { validUom.add(UOM.OUNCES.getName()); }
		if (conversion.getTeaSpoons() != null) { validUom.add(UOM.TSPS.getName()); }
		if (conversion.getTableSpoons() != null) { validUom.add(UOM.TBSP.getName()); }
		return validUom;
	}
	

}
