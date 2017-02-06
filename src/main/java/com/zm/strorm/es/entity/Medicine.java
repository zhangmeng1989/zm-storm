package com.zm.strorm.es.entity;

public class Medicine {

	private Integer id;// id

	private String name;// 名称

	private String function;// 功能

	private double price;// 价格

	public Medicine() {
		super();
	}

	public Medicine(Integer id, String name, double price, String function) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.function = function;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Medicine [id=" + id + ", name=" + name + ", function=" + function + ", price=" + price + "]";
	}

}
