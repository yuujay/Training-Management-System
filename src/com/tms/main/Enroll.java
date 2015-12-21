package com.tms.main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tms.beans.Trainee;
import com.tms.control.DBManager;

public class Enroll {

	Trainee trainee;
	Training training;

	public void withdrawTraining(Trainee trainee, Training training) {
		Statement statement = null;
		Statement selectStatement = null;
		Connection conn = null;
		statement = DBManager.connect(conn);
		selectStatement = DBManager.connect(conn);
		try {
			String strength = null;
			int strengthInt = 0;
			ResultSet rs = selectStatement.executeQuery(
					"SELECT Strength from Training where TrainingID = '" + training.getTrainingID() + "'");
			if (rs.next())
				strength = rs.getString(1);
			strengthInt = Integer.parseInt(strength);
			strengthInt = strengthInt + 1;

			statement.executeUpdate("DELETE FROM ENROLLMENT	" + "WHERE `TraineeID` ='" + trainee.getTraineeID()
					+ "' AND `TrainingID` = '" + training.getTrainingID() + "'");
			statement.executeUpdate("UPDATE Training SET Strength = '" + strengthInt + "' WHERE TrainingID = '"
					+ training.getTrainingID() + "'");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBManager.close(statement, conn);
	}

	public void enrollTraining(Trainee trainee, Training train) {
		// TODO Auto-generated method stub
		Statement statement = null;
		Statement selectStatement = null;
		Connection conn = null;
		statement = DBManager.connect(conn);
		selectStatement = DBManager.connect(conn);
		try {
			String strength = null;
			int strengthInt = 0;
			ResultSet rs = selectStatement
					.executeQuery("SELECT Strength from Training where TrainingID = '" + train.getTrainingID() + "'");
			if (rs.next())
				strength = rs.getString(1);
			strengthInt = Integer.parseInt(strength);
			strengthInt = strengthInt - 1;

			statement.executeUpdate(
					"INSERT INTO enrollment values ('" + train.getTrainingID() + "','" + trainee.getTraineeID() + "')");
			statement.executeUpdate("UPDATE Training SET Strength = '" + strengthInt + "' WHERE TrainingID = '"
					+ train.getTrainingID() + "'");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBManager.close(statement, conn);
	}
}
