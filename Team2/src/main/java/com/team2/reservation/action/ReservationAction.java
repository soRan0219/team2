package com.team2.reservation.action;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team2.admin.db.ParkingDTO;
import com.team2.admin.db.ResDTO;
import com.team2.commons.Action;
import com.team2.commons.ActionForward;
import com.team2.parkingdetail.db.PDetailDTO;
import com.team2.reservation.db.ResDAO;
import com.team2.reservation.db.ResDTO;

public class ReservationAction implements Action {
	
	public Time stringToTime(String time) {
		Time parsedTime = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm", Locale.ENGLISH);
			java.util.Date parsedDate = dateFormat.parse(time);
			parsedTime = new Time(parsedDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return parsedTime;
	} //stringToTime()

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
		Time parkInTime = stringToTime(fromTime);
		Time parkOutTime = stringToTime(toTime);
		
		request.setAttribute("parkInTime", parkInTime);
		request.setAttribute("parkOutTime", parkOutTime);
		
		//주차장 정보 조회
		String parkingCode = request.getParameter("parking");
		System.out.println("parkingCode = " + parkingCode);
		
		ResDAO dao = new ResDAO();
		ParkingDTO pDto = dao.getParking(parkingCode);
		request.setAttribute("pDto", pDto);
		
		//해당 주차장 모든 자리
		List<PDetailDTO> allList = dao.getAllParkingDetail(parkingCode);
		request.setAttribute("allList", allList);
		
		//예약 가능 주차 자리 조회
		ResDTO rDto = new ResDTO();
		rDto.setParkingCode(parkingCode);
		rDto.setParkInTime(parkInTime);
		rDto.setParkOutTime(parkOutTime);
		rDto.setResDate(resDate);
		
		List<PDetailDTO> available = dao.getAvailable(rDto);
		request.setAttribute("available", available);
		
		//결제 예상금액
		int price = dao.getPrice(parkInTime, parkOutTime);
		request.setAttribute("price", price);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./reservation/reservation.jsp");
		forward.setRedirect(false);
		
		return forward;
	} //execute()

} //ReservationAction
