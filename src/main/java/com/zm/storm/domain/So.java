package com.zm.storm.domain;

public class So extends Pa {

	private String soName;
	private int soAge;
	public String getSoName() {
		return soName;
	}
	public void setSoName(String soName) {
		this.soName = soName;
	}
	public int getSoAge() {
		return soAge;
	}
	public void setSoAge(int soAge) {
		this.soAge = soAge;
	}
	@Override
	public String toString() {
		return "So [soName=" + soName + ", soAge=" + soAge + "]";
	}
	
	
}
