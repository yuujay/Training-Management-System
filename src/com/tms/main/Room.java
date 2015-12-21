package com.tms.main;

public class Room {
	private String roomID;
	private String roomName;
	private int roomCapacity;

	public Room(String rID, int capacity) {
		this.roomID = rID;
		this.roomCapacity = capacity;
	}
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	
	public int getRoomCapacity() {
		return roomCapacity;
	}
	public void setRoomCapacity(int roomCapacity) {
		this.roomCapacity = roomCapacity;
	}
}
