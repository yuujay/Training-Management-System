package com.tms.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

	public static Statement connect(Connection conn) {

		Statement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://localhost/test",
							"muneer", "1234567");
			stmt = conn.createStatement();
			//System.out.println(stmt);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stmt;
	}
	
	public static void close(Statement stmt, Connection conn) {
		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
}
