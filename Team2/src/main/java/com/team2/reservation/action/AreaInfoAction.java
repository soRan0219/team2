package com.team2.reservation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team2.commons.Action;
import com.team2.commons.ActionForward;

public class AreaInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M: AreaInfoAction_execute()");
		
		String selected = request.getParameter("selected");
		System.out.println(selected);
		
		String parkingCode = selected.substring(0,1);
		String parkingPosition = selected.substring(1);
//		System.out.println(parkingCode + ", " + parkingPosition);
		
		
		
		
		return null;
	} //execute()

} //AreaInfoAction
