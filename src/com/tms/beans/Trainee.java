package com.tms.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.tms.control.DBManager;
import com.tms.main.Training;

public class Trainee extends Person{

	private String traineeID;

	public Trainee(String id) {
		// TODO Auto-generated constructor stub
		this.traineeID = id;
		this.loadPersonDetails(this.traineeID);
	}

	public String getTraineeID() {
		return traineeID;
	}

	public void setTraineeID(String traineeID) {
		this.traineeID = traineeID;
	}
	
	public List<Training> getEnrolledTrainings(){
		//write logic to fetch trainings enrolled for the trainee.
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		
		statement = DBManager.connect(conn);
		
		try {

			List<Training> trainingList = new ArrayList<Training>();

			rs = statement.executeQuery("select TrainingID, Title, Category from TRAINING WHERE TrainingID IN"
					+ "(SELECT TrainingID FROM enrollment WHERE TraineeID = '" + this.traineeID +"')");
			
			while(rs.next()){
				Training t = new Training();
				t.setTrainingID(rs.getString(1));
				t.setTrainingName(rs.getString(2));
				t.setTrainingType(rs.getString(3));
				trainingList.add(t);
			}

			return trainingList;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.close(statement, conn);
		}
		return null;		
	}
	
	public List<Training> getAvailableTrainings(){
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		
		statement = DBManager.connect(conn);
		
		try {

			List<Training> trainingList = new ArrayList<Training>();

			rs = statement.executeQuery("select TrainingID, Title, Category from TRAINING WHERE TrainingID NOT IN"
					+ "(SELECT TrainingID FROM enrollment WHERE TraineeID = '" + this.traineeID +"')");
			
			while(rs.next()){
				Training t = new Training();
				t.setTrainingID(rs.getString(1));
				t.setTrainingName(rs.getString(2));
				t.setTrainingType(rs.getString(3));
				trainingList.add(t);
			}

			return trainingList;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.close(statement, conn);
		}
		return null;		
	}
}
