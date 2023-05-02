package com.team2.reservation.db;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team2.commons.Action;
import com.team2.commons.ActionForward;

public class AvailableAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M: AvailableAction_execute()");
		
		//희망 예약 날짜 
		String dateString = request.getParameter("selectedDate");
		Date resDate = Date.valueOf(LocalDate.parse(dateString));
		request.setAttribute("resDate", resDate);
		
		//희망 입,출차 시간 
		String fromTime = request.getParameter("fromTime");
		String toTime = request.getParameter("toTime");
		Time parkInTime = Time.valueOf(LocalTime.parse(fromTime));
		Time parkOutTime = Time.valueOf(LocalTime.parse(toTime));
		request.setAttribute("parkInTime", parkInTime);
		request.setAttribute("parkOutTime", parkOutTime);
		
		
		
		return null;
	} //execute()

} //AvailableAction
