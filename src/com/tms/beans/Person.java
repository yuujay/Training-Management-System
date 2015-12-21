package com.tms.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tms.control.DBManager;

public class Person {
	private String name;
	private String contactNo;
	private String emailID;
	private String address;
	private String age;
	private String department;
	
	public String getName() {
		return name;
	}
	public String getContactNo() {
		return contactNo;
	}
	public String getEmailID() {
		return emailID;
	}
	public String getAddress() {
		return address;
	}
	public String getAge() {
		return age;
	}
	public String getDepartment() {
		return department;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public void loadPersonDetails(String ID){
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		
		statement = DBManager.connect(conn);
		try {
			rs = statement.executeQuery("select Name, Contact_number, EmailID, Address, Age, Department "
					+ "from user_details where ID = '" + ID +"'");
			while(rs.next()){
				this.name = rs.getString(1);
				this.contactNo = rs.getString(2);
				this.emailID = rs.getString(3);
				this.age = rs.getString(5);
				this.address = rs.getString(4);
				this.department = rs.getString(6);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBManager.close(statement, conn);
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", contactNo=" + contactNo + ", emailID=" + emailID + ", address=" + address
				+ ", age=" + age + ", department=" + department + "]";
	}
	
	
}
