package com.freeloader.conversionservice.model;

public record ConversionResponse(String food,String fromUnit, double fromAmount, String targetUnit, double targetAmount) {

}
