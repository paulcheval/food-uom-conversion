package com.freeloader.conversionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConversionExceptionController {
	@ExceptionHandler(value = FoodConversionAlreadyExistsException.class)
	public ResponseEntity<Object> exception(FoodConversionAlreadyExistsException exception) {
		return new ResponseEntity<Object>("Conversion already exists", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = UnknownConversionExcpetion.class)
	public ResponseEntity<Object> exception(UnknownConversionExcpetion exception) {
		return new ResponseEntity<Object>("Unknown Error in conversion service", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = FoodConversionNotExistException.class)
	public ResponseEntity<Object> exception(FoodConversionNotExistException exception) {
		return new ResponseEntity<Object>("Conversion does not exist", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = FoodsNotExistException.class)
	public ResponseEntity<Object> exception(FoodsNotExistException exception) {
		return new ResponseEntity<Object>("Foods do not exist", HttpStatus.BAD_REQUEST);
	}


}
