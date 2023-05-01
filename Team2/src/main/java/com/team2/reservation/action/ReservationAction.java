package com.team2.reservation.action;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team2.commons.Action;
import com.team2.commons.ActionForward;
import com.team2.parking.db.ParkingDTO;
import com.team2.parkingdetail.db.PDetailDTO;
import com.team2.reservation.db.ResDTO;
import com.team2.reservation.db.resDAO;

public class ReservationAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M: ReservationAction_execute()");
		
		request.setCharacterEncoding("utf-8");
		
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
		
		//주차장 정보 조회
		String parkingCode = request.getParameter("parking");
		System.out.println("parkingCode = " + parkingCode);
		
		resDAO dao = new resDAO();
		ParkingDTO pDto = dao.getParking(parkingCode);
		request.setAttribute("pDto", pDto);
		
		//예약 가능 주차 자리 조회
//		PDetailDTO pdDto = new PDetailDTO();
		ResDTO rDto = new ResDTO();
		rDto.setParkingCode(parkingCode);
		rDto.setParkInTime(parkInTime);
		rDto.setParkOutTime(parkOutTime);
		rDto.setResDate(resDate);
		
		List<PDetailDTO> available = dao.getAvailable(rDto);
		request.setAttribute("available", available);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./reservation/reservation.jsp");
		forward.setRedirect(false);
		
		return forward;
	} //execute()

} //ReservationAction
