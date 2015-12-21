package com.tms.control;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.tms.beans.Administrator;
import com.tms.beans.Trainee;
import com.tms.main.ClassCatalog;
import com.tms.main.Enroll;
import com.tms.main.Room;
import com.tms.main.Schedule;
import com.tms.main.Training;

public class TMSController {

	public static String id;
	public static String type;
	private static SecureRandom random = new SecureRandom();
	
	public static void loginControl(HttpServletRequest request, HttpServletResponse response) {

		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		JSONObject obj = null;

		statement = DBManager.connect(conn);

		try {
			rs = statement.executeQuery("select ID, Type_of_User from Login where Username='"
					+ request.getParameter("userName") + "' and Password='" + request.getParameter("passWord") + "'");
			if (rs.next()) {
				String id = rs.getString(1);
				String type = rs.getString(2);
				System.out.println("ID: " + id);
				System.out.println("Type: " + type);
				setLoginData(id, type);
				obj = new JSONObject();
				obj.put("ID", id);
				obj.put("Type_of_User", type);
				// PrintWriter out = response.getWriter();
				// out.write(obj.toString());

				response.setContentType("text/html");
				request.setAttribute("LoginData", obj);
				if (type.equals("Emp")) {
					Trainee t = new Trainee(id);
					List<Training> trainings = t.getEnrolledTrainings();
					request.setAttribute("enrolledTrainingList", trainings);
					RequestDispatcher rd = request.getRequestDispatcher("Trainee.jsp");
					rd.forward(request, response);
				} else if (type.equals("Adm")) {
					Administrator admin = new Administrator(id);
					List<Training> trainings = admin.getAllTrainings();
					request.setAttribute("allTrainingList", trainings);
					RequestDispatcher rd = request.getRequestDispatcher("Admin.jsp");
					rd.forward(request, response);
				}

			} else {
				obj = new JSONObject();
				obj.put("result", "No user found");

				response.setContentType("text/html");
				request.setAttribute("errorMsg", obj);
				RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		DBManager.close(statement, conn);
	}

	private static void setLoginData(String id2, String type2) {
		// TODO Auto-generated method stub
		id = id2;
		type = type2;
	}

	public static void getEnrolledTrainings(HttpServletRequest request, HttpServletResponse response) {
		String empID = request.getParameter("empID");
		JSONObject obj = null;

		try {
			if (type.equals("Emp")) {
				Trainee t = new Trainee(empID);
				List<Training> trainingList = t.getEnrolledTrainings();

				obj = new JSONObject();
				obj.put("ID", id);
				obj.put("type", type);

				response.setContentType("text/html");
				request.setAttribute("LoginData", obj);
				request.setAttribute("enrolledTrainingList", trainingList);
				RequestDispatcher rd = request.getRequestDispatcher("Trainee.jsp");
				rd.forward(request, response);
			} else if (type.equals("Adm")) {
				Administrator adm = new Administrator(empID);
				List<Training> trainingList = adm.getAllTrainings();

				obj = new JSONObject();
				obj.put("ID", id);
				obj.put("type", type);

				response.setContentType("text/html");
				request.setAttribute("LoginData", obj);
				request.setAttribute("allTrainingList", trainingList);
				RequestDispatcher rd = request.getRequestDispatcher("Admin.jsp");
				rd.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void getProfileInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String empID = request.getParameter("empID");
		JSONObject obj = null;
		if (type.equals("Emp")) {
			Trainee t = new Trainee(empID);
			List<Trainee> tList = new ArrayList<Trainee>();
			try {
				obj = new JSONObject();
				obj.put("ID", id);
				obj.put("type", type);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tList.add(t);
			response.setContentType("text/html");

			request.setAttribute("traineeDetails", tList);
			request.setAttribute("LoginData", obj);
			RequestDispatcher rd = request.getRequestDispatcher("Trainee.jsp");
			rd.forward(request, response);
		} else if (type.equals("Adm")) {
			Administrator adm = new Administrator(empID);
			List<Administrator> admList = new ArrayList<Administrator>();
			try {
				obj = new JSONObject();
				obj.put("ID", id);
				obj.put("type", type);
			} catch (Exception e) {
				e.printStackTrace();
			}
			admList.add(adm);
			response.setContentType("text/html");

			request.setAttribute("adminDetails", admList);
			request.setAttribute("LoginData", obj);
			RequestDispatcher rd = request.getRequestDispatcher("Admin.jsp");
			rd.forward(request, response);
		}

	}

	public static void withdrawTraining(HttpServletRequest request, HttpServletResponse response) {
		String empID = request.getParameter("empID");
		String trainingID = request.getParameter("trainingID");
		JSONObject obj = null;

		try {
			obj = new JSONObject();
			obj.put("ID", id);
			obj.put("type", type);

			Trainee t = new Trainee(empID);
			Training train = new Training(trainingID);

			Enroll enr = new Enroll();
			enr.withdrawTraining(t, train);

			response.setContentType("text/html");
			request.setAttribute("LoginData", obj);
			request.setAttribute("message", "You have been withdrawn from the Training.");
			RequestDispatcher rd = request.getRequestDispatcher("Trainee.jsp");

			rd.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void findTraining(HttpServletRequest request, HttpServletResponse response) {
		String empID = request.getParameter("empID");
		JSONObject obj = null;

		try {
			obj = new JSONObject();
			obj.put("ID", id);
			obj.put("type", type);

			Trainee t = new Trainee(empID);
			List<Training> trainingList = t.getAvailableTrainings();

			response.setContentType("text/html");
			request.setAttribute("LoginData", obj);
			request.setAttribute("availableTrainingList", trainingList);
			RequestDispatcher rd = request.getRequestDispatcher("Trainee.jsp");

			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void enrollTraining(HttpServletRequest request, HttpServletResponse response) {
		String empID = request.getParameter("empID");
		String trainingID = request.getParameter("trainingID");
		JSONObject obj = null;

		try {
			obj = new JSONObject();
			obj.put("ID", id);
			obj.put("type", type);

			Trainee t = new Trainee(empID);
			Training train = new Training(trainingID);

			Enroll enr = new Enroll();
			enr.enrollTraining(t, train);

			response.setContentType("text/html");
			request.setAttribute("LoginData", obj);
			request.setAttribute("message", "You have been enrolled for the Training.");
			RequestDispatcher rd = request.getRequestDispatcher("Trainee.jsp");

			rd.forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void createNewTraining(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		JSONObject obj = null;
		String empID = request.getParameter("empID");

		String TrainingID = request.getParameter("TrainingID");
		String Title = request.getParameter("Title");
		String Category = request.getParameter("Category");
		String Hours = request.getParameter("Hours");
		String Strength = request.getParameter("Strength");
		
		String classID = new BigInteger(30, random).toString();
		statement = DBManager.connect(conn);

		try {
			statement.executeUpdate("INSERT INTO TRAINING values ('" + TrainingID + "','" + empID + "','" + Title
					+ "','" + Category + "'," + Hours + ",NULL, 'Pending','" + Strength + "')");

			int str = Integer.parseInt(Strength);
			Training train = new Training(TrainingID);
			Schedule s = new Schedule();
			Room r = s.getAvailableRoom(train);
			ClassCatalog c = new ClassCatalog(classID, str);
			
			statement.executeUpdate("Insert into ClassCatalog values('" + c.getClassID() + "','"+ c.getStrength() +"')");
			statement.executeUpdate("INSERT INTO Schedule values ('"+r.getRoomID() +"','"+ train.getTrainingID() +"','"+ c.getClassID() +"')");
			
			obj = new JSONObject();
			obj.put("ID", id);
			obj.put("type", type);

			Trainee t = new Trainee(empID);
			List<Training> trainingList = t.getEnrolledTrainings();

			response.setContentType("text/html");
			request.setAttribute("LoginData", obj);
			request.setAttribute("enrolledTrainingList", trainingList);
			request.setAttribute("message", "Training Created successfully");
			RequestDispatcher rd = request.getRequestDispatcher("Trainee.jsp");

			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void approveTrainingSession(HttpServletRequest request, HttpServletResponse response) {
		String empID = request.getParameter("empID");
		JSONObject obj = null;
		try {
			obj = new JSONObject();
			obj.put("ID", id);
			obj.put("type", type);

			Administrator admin = new Administrator(empID);
			admin.approveTrainingSession(request.getParameter("trainingID"));

			response.setContentType("text/html");
			request.setAttribute("LoginData", obj);
			request.setAttribute("message", "Training Approved");
			RequestDispatcher rd = request.getRequestDispatcher("Admin.jsp");

			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getUnapprovedTrainingList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String empID = request.getParameter("empID");
		JSONObject obj = null;
		try {
			obj = new JSONObject();
			obj.put("ID", id);
			obj.put("type", type);

			Administrator admin = new Administrator(empID);
			List<Training> trainingList = admin.getPendingTrainingList();

			response.setContentType("text/html");
			request.setAttribute("LoginData", obj);
			request.setAttribute("pendingTrainingList", trainingList);
			RequestDispatcher rd = request.getRequestDispatcher("Admin.jsp");

			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void rejectTrainingSession(HttpServletRequest request, HttpServletResponse response) {
		String empID = request.getParameter("empID");
		JSONObject obj = null;
		try {
			obj = new JSONObject();
			obj.put("ID", id);
			obj.put("type", type);

			Administrator admin = new Administrator(empID);
			admin.rejectTrainingSession(request.getParameter("trainingID"));

			response.setContentType("text/html");
			request.setAttribute("LoginData", obj);
			request.setAttribute("message", "Training Rejected");
			RequestDispatcher rd = request.getRequestDispatcher("Admin.jsp");

			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void logout(HttpServletRequest request, HttpServletResponse response) {
		String empID = request.getParameter("empID");

		try {
			response.setContentType("text/html");
			
			request.setAttribute("message", "Logged out");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");

			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
