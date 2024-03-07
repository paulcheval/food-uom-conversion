package com.freeloader.conversionservice;



public enum UOM {
	CUPS("cup"),
	GRAMS("g"),
	OUNCES("oz"),
	TSPS("tsp"),
	TBSP("tbsp"),
	UNKNOWN("unknown");
	
	private String name;

	UOM(String name) { this.name = name; }
	
	public String getName() {
		return name;
	}
	
	public UOM determineUom(String value) {
		if (valueOf(value) == null) { return UNKNOWN; }
		return valueOf(value);
	}

}
