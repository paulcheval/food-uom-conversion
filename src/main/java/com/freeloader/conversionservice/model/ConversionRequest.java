package com.freeloader.conversionservice.model;

public record ConversionRequest(String food, String fromUnit, double fromAmount, String targetUnit) {

}
