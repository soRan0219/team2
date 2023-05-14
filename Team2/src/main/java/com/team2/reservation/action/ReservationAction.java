package com.team2.reservation.action;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.team2.admin.db.ParkingDTO;
import com.team2.admin.db.ResDTO;
import com.team2.commons.Action;
import com.team2.commons.ActionForward;
import com.team2.commons.JSForward;
import com.team2.parkingdetail.db.PDetailDTO;
import com.team2.reservation.db.ResDAO;

public class ReservationAction implements Action {
	

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M: ReservationAction_execute()");
		
		request.setCharacterEncoding("utf-8");
		
		//희망 예약 날짜 
		String dateString = request.getParameter("selectedDate");
		Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		java.sql.Date resDate = new java.sql.Date(parsedDate.getTime());
//		System.out.println("resDate: " + resDate);
		request.setAttribute("resDate", resDate);
		
		//희망 입,출차 시간 
		String fromTime = request.getParameter("parkInTime");
		String toTime = request.getParameter("parkOutTime");
		fromTime += ":00";
		toTime += ":00";
		System.out.println(fromTime + ", " + toTime);
		
		Time parkInTime = Time.valueOf(fromTime);
		Time parkOutTime = Time.valueOf(toTime);
		
		request.setAttribute("parkInTime", parkInTime);
		request.setAttribute("parkOutTime", parkOutTime);
		
		//주차장 정보 조회
		String parkingCode = request.getParameter("parking");
		System.out.println("parkingCode = " + parkingCode);
		
		ResDAO dao = new ResDAO();
		ParkingDTO pDto = dao.getParking(parkingCode);
		request.setAttribute("pDto", pDto);
		
		//예약 가능 주차 자리 조회
		ResDTO rDto = new ResDTO();
		rDto.setParkingCode(parkingCode);
		rDto.setParkInTime(parkInTime);
		rDto.setParkOutTime(parkOutTime);
		rDto.setResDate(resDate);
		
		List<PDetailDTO> available = dao.getAvailable(rDto);
		request.setAttribute("available", available);
		
		if(available.size()==0) {
			JSForward.alertAndBack(response, "예약 가능한 자리가 없습니다.");
			return null;
		}
		
		String json = new Gson().toJson(available);
		request.setAttribute("aList", json);
		
		//결제 예상금액
		int price = dao.getPrice(parkInTime, parkOutTime);
		request.setAttribute("price", price);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./reservation/reservation.jsp");
		forward.setRedirect(false);
		
		return forward;
	} //execute()

} //ReservationAction
