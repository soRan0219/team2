package com.team2.parkingdetail.db;

public class PDetailDTO {
	private String parkingCode;
	private int parkingPosition;
	
	public String getParkingCode() {
		return parkingCode;
	}
	public void setParkingCode(String parkingCode) {
		this.parkingCode = parkingCode;
	}
	public int getParkingPosition() {
		return parkingPosition;
	}
	public void setParkingPosition(int parkingPosition) {
		this.parkingPosition = parkingPosition;
	}
	@Override
	public String toString() {
		return "PDetailDTO [parkingCode=" + parkingCode + ", parkingPosition=" + parkingPosition + "]";
	}
	
} //PDDTO
