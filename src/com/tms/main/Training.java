package com.tms.main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tms.control.DBManager;

public class Training {

	private String trainingID;
	private String trainingName;
	private String trainingType;
	private String noOfHours;
	private String startDate;
	private String status;
	private String Strength;

	public String getStrength() {
		return Strength;
	}

	public void setStrength(String strength) {
		Strength = strength;
	}

	public Training() {
		this.trainingID = null;
		this.trainingName = null;
		this.trainingType = null;
		this.noOfHours = null;
		this.startDate = null;
		this.status = null;
	}

	public String getNoOfHours() {
		return noOfHours;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setNoOfHours(String noOfHours) {
		this.noOfHours = noOfHours;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Training(String trainID){
		this.trainingID = trainID;
		this.loadTrainingDetails();
	}
	
	private void loadTrainingDetails() {
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		
		statement = DBManager.connect(conn);
		try {
			rs = statement.executeQuery("select Title, Category, No_of_hours, StartDate, Status, Strength "
					+ "from Training where TrainingID = '" + this.trainingID +"'");
			while(rs.next()){
				this.trainingName = rs.getString(1);
				this.trainingType = rs.getString(2);
				this.noOfHours = rs.getString(3);
				this.startDate = rs.getString(4);
				this.status = rs.getString(5);
				this.Strength = rs.getString(6);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBManager.close(statement, conn);
		
	}

	public String getTrainingID() {
		return trainingID;
	}
	public void setTrainingID(String trainingID) {
		this.trainingID = trainingID;
	}
	public String getTrainingName() {
		return trainingName;
	}
	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
	public String getTrainingType() {
		return trainingType;
	}
	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}
	
	public void cancelTraining(){
		
	}
	
	public void updateTraining(){
		
	}
	
}
