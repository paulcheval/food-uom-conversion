package com.freeloader.conversionservice.conversion;

import static com.freeloader.conversionservice.UOM.CUPS;
import static com.freeloader.conversionservice.UOM.GRAMS;
import static com.freeloader.conversionservice.UOM.OUNCES;
import static com.freeloader.conversionservice.UOM.TBSP;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.task.TaskSchedulingProperties;

import com.freeloader.conversionservice.UOM;
import com.freeloader.conversionservice.db.entities.FoodConversion;
import com.freeloader.conversionservice.model.ConversionRequest;
import com.freeloader.conversionservice.model.ConversionResponse;

class FoodConversionConverterTest {
	
	private static final String FLOUR = "Flour";
	private static final FoodConversion FOOD_CONVERSION = new FoodConversion(FLOUR, 1.0, 2.0, 3.0, 4.0, 5.0);

	@Test
	void ensureValidUomReturnCupsGramsOuncesWhenCupsGramsOuncesPopulated() {
		List<String> validUoms = FoodConversionConverter.determineValidUom(new FoodConversion(FLOUR, null, null, 1.0, 2.0, 3.0));
		assertEquals(3, validUoms.size());
		assertTrue(validUoms.containsAll(asList(CUPS.getName(), GRAMS.getName(), OUNCES.getName())));
	}
	
	@Test
	void ensureValidUomReturnTeaspoonsTablesSpoonsCupsGramsOuncesWhenCupsGramsOuncesPopulated() {
		List<String> validUoms = FoodConversionConverter.determineValidUom(new FoodConversion(FLOUR, 1.0, 1.0, 1.0, 2.0, 3.0));
		assertEquals(5, validUoms.size());
		assertTrue(validUoms.containsAll(asList(CUPS.getName(), GRAMS.getName(), OUNCES.getName(), TBSP.getName(), TBSP.getName())));
	}
	
	@Test
	void ensureValidUomReturnCupsWhenCupsPopulated() {
		List<String> validUoms = FoodConversionConverter.determineValidUom(new FoodConversion(FLOUR, null, null, 1.0, null, null));
		assertEquals(1, validUoms.size());
		assertTrue(validUoms.containsAll(asList(CUPS.getName())));
	}
	
	@Test
	void ensureValidUomReturnGramsWhenGramsPopulated() {
		List<String> validUoms = FoodConversionConverter.determineValidUom(new FoodConversion(FLOUR, null, null, null, 1.0, null));
		assertEquals(1, validUoms.size());
		assertTrue(validUoms.containsAll(asList(GRAMS.getName())));
	}

	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsNullWhenFoodConversionIsNull() {
		assertNull(FoodConversionConverter.determineAmountBasedOnInputUnit(null, FOOD_CONVERSION));
	}
	
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsNullWhenCOnversionRequestIsNull() {
		assertNull(FoodConversionConverter.determineAmountBasedOnInputUnit(new ConversionRequest(FLOUR, CUPS.getName(), 1, GRAMS.getName()), null));
	}
	
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedCupsWhenTargetIsGrams() {
		assertEquals(4.0/3.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.CUPS.getName(), 1, UOM.GRAMS.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedTspsWhenTargetIsGrams() {
		assertEquals(4.0/1.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.TSPS.getName(), 1, UOM.GRAMS.getName()), FOOD_CONVERSION).targetAmount());
	}

	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedTbpsWhenTargetIsGrams() {
		assertEquals(2.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.TBSP.getName(), 1, UOM.GRAMS.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedOuncesWhenTargetIsGrams() {
		assertEquals(4.0/5.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.OUNCES.getName(), 1, UOM.GRAMS.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedGramsWhenTargetIsGrams() {
		assertEquals(1.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.GRAMS.getName(), 1, UOM.GRAMS.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedCupsWhenTargetIsOunces() {
		assertEquals(5.0/3.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.CUPS.getName(), 1, UOM.OUNCES.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedTspsWhenTargetIsOunces() {
		assertEquals(5.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.TSPS.getName(), 1, UOM.OUNCES.getName()), FOOD_CONVERSION).targetAmount());
	}

	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedTbpsWhenTargetIsOunces() {
		assertEquals(2.5, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.TBSP.getName(), 1, UOM.OUNCES.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedOuncesWhenTargetIsOunces() {
		assertEquals(1.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.OUNCES.getName(), 1, UOM.OUNCES.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedGramsWhenTargetIsOunces() {
		assertEquals(1.25, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.GRAMS.getName(), 1, UOM.OUNCES.getName()), FOOD_CONVERSION).targetAmount());
	}
	
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedCupsWhenTargetIsCups() {
		assertEquals(1.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.CUPS.getName(), 1, UOM.CUPS.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedTspsWhenTargetIsCups() {
		assertEquals(3.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.TSPS.getName(), 1, UOM.CUPS.getName()), FOOD_CONVERSION).targetAmount());
	}

	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedTbpsWhenTargetIsCups() {
		assertEquals(3.0/2.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.TBSP.getName(), 1, UOM.CUPS.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedOuncesWhenTargetIsCups() {
		assertEquals(3.0/5.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.OUNCES.getName(), 1, UOM.CUPS.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedGramsWhenTargetIsCups() {
		assertEquals(3.0/4.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.GRAMS.getName(), 1, UOM.CUPS.getName()), FOOD_CONVERSION).targetAmount());
	}
	
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedCupsWhenTargetIsTsps() {
		assertEquals(1.0/3.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.CUPS.getName(), 1, UOM.TSPS.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedTspsWhenTargetIsTsps() {
		assertEquals(1.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.TSPS.getName(), 1, UOM.TSPS.getName()), FOOD_CONVERSION).targetAmount());
	}

	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedTbpsWhenTargetIsTsps() {
		assertEquals(1.0/2.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.TBSP.getName(), 1, UOM.TSPS.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedOuncesWhenTargetIsps() {
		assertEquals(1.0/5.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.OUNCES.getName(), 1, UOM.TSPS.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedGramsWhenTargetIsTsps() {
		assertEquals(1.0/4.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.GRAMS.getName(), 1, UOM.TSPS.getName()), FOOD_CONVERSION).targetAmount());
	}
	
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedCupsWhenTargetIsTbps() {
		assertEquals(2.0/3.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.CUPS.getName(), 1, UOM.TBSP.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedTspsWhenTargetIsTbps() {
		assertEquals(2.0/1.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.TSPS.getName(), 1, UOM.TBSP.getName()), FOOD_CONVERSION).targetAmount());
	}

	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedTbpsWhenTargetIsTbps() {
		assertEquals(1.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.TBSP.getName(), 1, UOM.TBSP.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedOuncesWhenTargetIbps() {
		assertEquals(2.0/5.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.OUNCES.getName(), 1, UOM.TBSP.getName()), FOOD_CONVERSION).targetAmount());
	}
	@Test
	void ensureDetermineAmountBasedOnInputUnitReturnsConvertedGramsWhenTargetIsTbps() {
		assertEquals(2.0/4.0, FoodConversionConverter.determineAmountBasedOnInputUnit(
				new ConversionRequest(FLOUR, UOM.GRAMS.getName(), 1, UOM.TBSP.getName()), FOOD_CONVERSION).targetAmount());
	}
	

	


}
