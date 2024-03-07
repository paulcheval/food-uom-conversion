package com.freeloader.conversionservice.db.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties
public class FoodConversion {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String food;
	private Double teaSpoons;
	private Double tableSpoons;
	private Double cups;
	private Double grams;
	private Double ounces;
	
	protected FoodConversion() {}
	
	public FoodConversion( String food, Double teaSpoons, Double tableSpoons, Double cups, Double grams,
			Double ounces) {
		super();		
		this.food = food;
		this.teaSpoons = teaSpoons;
		this.tableSpoons = tableSpoons;
		this.cups = cups;
		this.grams = grams;
		this.ounces = ounces;
	}
	
	public FoodConversion(String food, double cups, double grams, double ounces) {
		super();		
		this.food = food;
		this.teaSpoons = null;
		this.tableSpoons = null;
		this.cups = cups;
		this.grams = grams;
		this.ounces = ounces;
	}
	
	public FoodConversion(String food, Double teaSpoons, Double grams, Double ounces) {
		super();		
		this.food = food;
		this.teaSpoons = teaSpoons;
		this.tableSpoons = null;
		this.cups = null;
		this.grams = grams;
		this.ounces = ounces;
	}
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	public String getFood() { return food; }
	public void setFood(String food) { this.food = food; }

	public Double getTeaSpoons() { return teaSpoons; }
	public void setTeaSpoons(Double teaSpoons) { this.teaSpoons = teaSpoons; }
	
	public Double getTableSpoons() { return tableSpoons; }
	public void setTableSpoons(Double tableSpoons) { this.tableSpoons = tableSpoons; }
	
	public Double getCups() { return cups; }
	public void setCups(Double cups) { this.cups = cups; }
	
	public Double getGrams() { return grams; }	
	public void setGrams(Double grams) { this.grams = grams; }
	
	public Double getOunces() { return ounces; }
	public void setOunces(Double ounces) { this.ounces = ounces; }
	
	@Override
	public String toString() {
		return "FoodConversion [id=" + id + ", food=" + food + ", teaSpoons=" + teaSpoons + ", tableSpoons="
				+ tableSpoons + ", cups=" + cups + ", grams=" + grams + ", ounces=" + ounces + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, food, cups, grams, ounces, tableSpoons, teaSpoons);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodConversion other = (FoodConversion) obj;
		return Objects.equals(cups, other.cups) && Objects.equals(grams, other.grams) && Objects.equals(id, other.id)
				&& Objects.equals(food, other.food) && Objects.equals(ounces, other.ounces)
				&& Objects.equals(tableSpoons, other.tableSpoons) && Objects.equals(teaSpoons, other.teaSpoons);
	}
	
	
}
