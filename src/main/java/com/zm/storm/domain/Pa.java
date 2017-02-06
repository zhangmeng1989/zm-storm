package com.zm.storm.domain;

public class Pa {
	
	private String paName;
	private int paAge;
	public String getPaName() {
		return paName;
	}
	public void setPaName(String paName) {
		this.paName = paName;
	}
	public int getPaAge() {
		return paAge;
	}
	public void setPaAge(int paAge) {
		this.paAge = paAge;
	}
	@Override
	public String toString() {
		return "Pa [paName=" + paName + ", paAge=" + paAge + "]";
	}
}
