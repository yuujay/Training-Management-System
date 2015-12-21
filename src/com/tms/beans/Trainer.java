package com.tms.beans;

public class Trainer extends Person{
	private String trainerID;
	
	public void getTrainings(){
		//write logic to fetch trainings for particular trainer ID
	}

	public String getTrainerID() {
		return trainerID;
	}

	public void setTrainerID(String trainerID) {
		this.trainerID = trainerID;
	}
	
	public void requestNewTraining(){
		
	}
}
