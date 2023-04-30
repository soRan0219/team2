package com.team2.reservation.action;

import java.sql.Date;
import java.time.LocalDate;

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
		
		String dateString = request.getParameter("selectedDate");
		Date selectedDate = Date.valueOf(LocalDate.parse(dateString));
		request.setAttribute("selectedDate", selectedDate);
		
		String parkingCode = request.getParameter("parking");
		System.out.println("parkingCode = " + parkingCode);
		
		ResDTO rDto = new ResDTO();
		PDetailDTO pdDto = new PDetailDTO();
		
		resDAO dao = new resDAO();
		ParkingDTO pDto = dao.getParking(parkingCode);
		
		request.setAttribute("pDto", pDto);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./reservation/reservation.jsp");
		forward.setRedirect(false);
		
		return forward;
	} //execute()

} //ReservationAction
