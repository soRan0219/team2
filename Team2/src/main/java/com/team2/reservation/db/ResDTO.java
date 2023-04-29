package com.team2.reservation.db;

import java.sql.Date;
import java.sql.Time;

public class ResDTO {
	private int resNo;
	private String id;
	private String parkingCode;
	private int parkingPosition;
	private Date resDate;
	private Time parkInTime;
	private Time parkOutTime;
	private int price;
	private String carNo;
	private int resStatus;
	
	public int getResNo() {
		return resNo;
	}
	public void setResNo(int resNo) {
		this.resNo = resNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public Date getResDate() {
		return resDate;
	}
	public void setResDate(Date resDate) {
		this.resDate = resDate;
	}
	public Time getParkInTime() {
		return parkInTime;
	}
	public void setParkInTime(Time parkInTime) {
		this.parkInTime = parkInTime;
	}
	public Time getParkOutTime() {
		return parkOutTime;
	}
	public void setParkOutTime(Time parkOutTime) {
		this.parkOutTime = parkOutTime;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public int getResStatus() {
		return resStatus;
	}
	public void setResStatus(int resStatus) {
		this.resStatus = resStatus;
	}
	
} //ResDTO
