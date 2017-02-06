package com.zm.strorm.es.test;

/**
 * Created by ZXL on 2016/12/26
 */
public class myFormat {
	private Integer id;// id
	private String name;// 名称
	private String function;// 功能

	public myFormat() {
		super();
	}

	public myFormat(Integer id, String name, String function) {
		super();
		this.id = id;
		this.name = name;
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
}