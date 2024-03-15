package com.freeloader.conversionservice.sd.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void init() {
		//jdbcTemplate.update("insert into FOOD_CONVERSION (id,  food, cups, grams, ounces) values (?, ?, ?, ?, ?)",
		//		new Object[] {'1',  "Flour", 1.0, 120.0, 4.25 });
		//jdbcTemplate.update("insert into FOOD_CONVERSION (id, food, cups, grams, ounces) values (?, ?, ?, ?, ?)",
		//		new Object[] { 2,  "Apple Sauce", 1.0, 255.0, 9.0 });
		//jdbcTemplate.update("insert into FOOD_CONVERSION (id, food, cups, grams, ounces) values (?, ?, ?, ?, ?)",
		//		new Object[] { 3,  "Grated Carrots", 1.0, 99.0, 3.5 });
		/*
		 * jdbcTemplate.
		 * update("insert into FOOD_CONVERSION (id, food, tableSpoons, grams, ounces) values (?, ?, ?, ?, ?)"
		 * , new Object[] { 4, "Tomato Paste", 1.0, 29.0, 1.0 }); jdbcTemplate.
		 * update("insert into FOOD_CONVERSION (id, food, teaSpoons, grams) values (?, ?, ?, ?)"
		 * , new Object[] { 5, "Baking Powder", 1.0, 4.0 }); jdbcTemplate.
		 * update("insert into FOOD_CONVERSION (id, food, teaSpoons, grams) values (?, ?, ?, ?)"
		 * , new Object[] { 6, "Baking Soda", 1.0, 6.0 });
		 */

	}

}

