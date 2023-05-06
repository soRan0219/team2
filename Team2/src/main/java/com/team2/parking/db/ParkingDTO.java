package com.team2.parking.db;

public class ParkingDTO {
	private String parkingCode;
	private String parkingName;
	private String parkingAdr;
	private String inOutDoor;
	private int parkingTotal;
	private String parkingTel;
	
	public String getParkingTel() {
		return parkingTel;
	}
	public void setParkingTel(String parkingTel) {
		this.parkingTel = parkingTel;
	}
	public String getParkingCode() {
		return parkingCode;
	}
	public void setParkingCode(String parkingCode) {
		this.parkingCode = parkingCode;
	}
	public String getParkingName() {
		return parkingName;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	public String getParkingAdr() {
		return parkingAdr;
	}
	public void setParkingAdr(String parkingAdr) {
		this.parkingAdr = parkingAdr;
	}
	public String getInOutDoor() {
		return inOutDoor;
	}
	public void setInOutDoor(String inOutDoor) {
		this.inOutDoor = inOutDoor;
	}
	public int getParkingTotal() {
		return parkingTotal;
	}
	public void setParkingTotal(int parkingTotal) {
		this.parkingTotal = parkingTotal;
	}
	
	@Override
	public String toString() {
		return "ParkingDTO [parkingCode=" + parkingCode + ", parkingName=" + parkingName + ", parkingAdr=" + parkingAdr
				+ ", inOutDoor=" + inOutDoor + ", parkingTotal=" + parkingTotal + ", parkingTel=" + parkingTel + "]";
	}
	
} //ParkingDTO
