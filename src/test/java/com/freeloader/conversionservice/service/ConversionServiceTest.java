package com.freeloader.conversionservice.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.freeloader.conversionservice.UOM;

class ConversionServiceTest {

	@Test
	void test() {
		System.out.println(UOM.CUPS.getName());
		System.out.println(UOM.CUPS.name());
	}

}
