package com.tms.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.tms.beans.Trainee;
import com.tms.control.DBManager;
import com.tms.control.TMSController;
import com.tms.main.Training;

/**
 * Servlet implementation class TMSServlet
 */
@WebServlet("/TMSServlet")
public class TMSServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TMSServlet() {
        // TODO Auto-generated constructor stub
    }
    
    public static String getRequestContent(HttpServletRequest request)
			throws IOException {

		String body = null;
		StringBuilder sb = new StringBuilder();
		BufferedReader bufferedReader = null;

		//String hell = request.getParameter("hello");
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					sb.append(charBuffer, 0, bytesRead);
				}
			} else {
				sb.append("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		body = sb.toString();
		//System.out.println("Hello : " + hell);
		System.out.println("body: " + body);
		return body;
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		JSONObject json = null;
		int fromRequest = -1;
		
		
		try {
			System.out.println("Initalised");
			System.out.println(request.getParameter("hiddenValue"));
			fromRequest = Integer.parseInt(request.getParameter("hiddenValue"));
			System.out.println(fromRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		switch (fromRequest) {
		case 1: //sign-in
			TMSController.loginControl(request, response);
			break;
		
		case 2: //load enrolled trainings
			TMSController.getEnrolledTrainings(request, response);
			break;
			
		case 3: //profile page
			TMSController.getProfileInfo(request, response);
			break;
			
		case 4: //withdraw training for a trainee
			TMSController.withdrawTraining(request, response);
			break;
		
		case 5: //find training for trainee to enroll.
			TMSController.findTraining(request, response);
			break;
			
		case 6: //enroll a particular trainee for a training
			TMSController.enrollTraining(request, response);
			break;
			
		case 7: //create new training.
			TMSController.createNewTraining(request, response);
			break;
			
		case 8: //fetch pending Training session
			TMSController.getUnapprovedTrainingList(request, response);
			break;
			
		case 9: //approve training session
			TMSController.approveTrainingSession(request, response);
			break;
			
		case 10: //reject a training session
			TMSController.rejectTrainingSession(request, response);
			break;
			
		case 11: //logout
			TMSController.logout(request, response);
			break;
		}
	}

}
