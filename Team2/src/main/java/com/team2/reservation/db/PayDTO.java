package com.team2.reservation.db;

import java.sql.Date;

public class PayDTO {
	private String payNo;
	private String payWay;
	private String payCondition;
	private Date payDate;
	private int totalPrice;
	
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getPayCondition() {
		return payCondition;
	}
	public void setPayCondition(String payCondition) {
		this.payCondition = payCondition;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Override
	public String toString() {
		return "PayDTO [payNo=" + payNo + ", payWay=" + payWay + ", payCondition=" + payCondition + ", payDate="
				+ payDate + ", totalPrice=" + totalPrice + "]";
	}
	
} //PayDTO
