package com.team2.reservation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team2.commons.Action;
import com.team2.commons.ActionForward;
import com.team2.parking.db.ParkingDTO;
import com.team2.parkingdetail.db.PDDTO;
import com.team2.reservation.db.ResDTO;

public class ReservationAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M: ReservationAction_execute()");
		
		request.setCharacterEncoding("utf-8");
		
		String parkingCode = request.getParameter("parking");
		System.out.println("parkingCode = " + parkingCode);
		
		ParkingDTO pDto = new ParkingDTO();
		pDto.setParkingCode(parkingCode);
		
		ResDTO rDto = new ResDTO();
		PDDTO pdDto = new PDDTO();
		
		
		
		return null;
	} //execute()

} //ReservationAction
