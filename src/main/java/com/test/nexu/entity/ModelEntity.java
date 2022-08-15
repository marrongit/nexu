package com.test.nexu.entity;

public class ModelEntity {
	
	private int id;
	private String name;
	private int average_price;
	private String brand_name;

	public ModelEntity() {
		
	}
	
	public ModelEntity(int id, String name, int average_price, String brand_name) {
		this.id = id;
		this.name = name;
		this.average_price = average_price;
		this.brand_name = brand_name;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAverage_price() {
		return average_price;
	}

	public void setAverage_price(int average_price) {
		this.average_price = average_price;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	@Override
	public String toString() {
		return "ModelEntity [id=" + id + ", name=" + name + ", average_price=" + average_price + ", brand_name="
				+ brand_name + "]";
	}

}
