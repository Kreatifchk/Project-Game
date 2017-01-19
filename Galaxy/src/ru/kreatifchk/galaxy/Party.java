package ru.kreatifchk.galaxy;

public class Party {
	
	private String name;
	
	private int trust;
	
	public Party (String name, int trust) {
		this.name = name;
		this.trust = trust;
	}
	
	public Party (String name) {
		this.name = name;
	}
	
	public Party() {
	}

	public String getName() {
		return name;
	}
	public int getTrust() {
		return trust;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setTrust(int trust) {
		this.trust = trust;
	}
	
}
