package com.tms.main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.tms.control.DBManager;

public class Schedule {
	private Room r;
	private ClassCatalog clas;
	
	public Schedule(){
		this.r = null;
		this.clas = null;
	}
	
	public Room getR() {
		return r;
	}
	public ClassCatalog getClas() {
		return clas;
	}
	public void setR(Room r) {
		this.r = r;
	}
	public void setC(ClassCatalog c) {
		this.clas = c;
	}
	
	public Room getAvailableRoom(Training t){
		String Strength = t.getStrength();
		String roomID = null;
		int str = Integer.parseInt(Strength);
		int capacity = 0;
		Room room = null;
		Statement statement = null;
		ResultSet rs = null;
		Connection conn = null;
		
		statement = DBManager.connect(conn);
		
		try{
			rs = statement.executeQuery("Select RoomID, Capacity from Room where Capacity >= '"+ str +"'");
			
			if(rs.next()){
				roomID = rs.getString(1);
				capacity = rs.getInt(2);
			}
			
			room = new Room(roomID, capacity);
			return room;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
