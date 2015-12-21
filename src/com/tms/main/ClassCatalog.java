package com.tms.main;

public class ClassCatalog {
	private String classID;
	private int Strength;
	
	public ClassCatalog(String id, int str){
		this.classID = id;
		this.Strength = str;
	}
	
	public String getClassID() {
		return classID;
	}
	public int getStrength() {
		return Strength;
	}
	public void setClassID(String classID) {
		this.classID = classID;
	}
	public void setStrength(int strength) {
		Strength = strength;
	}
	
}
