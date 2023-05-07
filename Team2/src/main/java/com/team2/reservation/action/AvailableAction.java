package com.team2.reservation.action;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.team2.admin.db.ResDTO;
import com.team2.commons.Action;
import com.team2.commons.ActionForward;
import com.team2.parkingdetail.db.PDetailDTO;
import com.team2.reservation.db.ResDAO;

public class AvailableAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M: AvailableAction_execute()");
		
		request.setCharacterEncoding("utf-8");
		
		//희망 예약 날짜 
		String dateString = request.getParameter("date");
		System.out.println("dateString: " + dateString);
		
		Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		java.sql.Date resDate = new java.sql.Date(parsedDate.getTime());
//		System.out.println("resDate: " + resDate);
		
		//희망 입,출차 시간 
		String fromTime = request.getParameter("fromTime");
		String toTime = request.getParameter("toTime");
		fromTime += ":00";
		toTime += ":00";
//		System.out.println("fromTime: " + fromTime);
//		System.out.println("toTime: " + toTime);
		
		Time parkInTime = Time.valueOf(fromTime);
		Time parkOutTime = Time.valueOf(toTime);
//		System.out.println("parkInTime: " + parkInTime);
//		System.out.println("parkOutTime" + parkOutTime);
		
		//주차장 잔여 자리 정보 조회
		String parkingCode = request.getParameter("parkingCode");
//		System.out.println("parkingCode = " + parkingCode);
		
		ResDTO rDto = new ResDTO();
		rDto.setParkingCode(parkingCode);
		rDto.setParkInTime(parkInTime);
		rDto.setParkOutTime(parkOutTime);
		rDto.setResDate(resDate);
		
		ResDAO dao = new ResDAO();
		
		int price = dao.getPrice(parkInTime, parkOutTime);
//		System.out.println("price: " + price);
		List<PDetailDTO> aList = dao.getAvailable(rDto);
		
		JsonArray jArr = new JsonArray();
		
		Iterator it = aList.iterator();
		if(it.hasNext()) {
			for(int i=0; i<aList.size(); i++) {
				JsonObject jobj = new JsonObject();
				jobj.addProperty("parkingCode", aList.get(i).getParkingCode());
				jobj.addProperty("parkingPosition", aList.get(i).getParkingPosition());
				
				jArr.add(jobj);
			}
		}
		JsonObject jobj = new JsonObject();
		jobj.addProperty("price", price);
		jArr.add(jobj);
		
		System.out.println(jArr.size());
		
		response.setContentType("application/x-json; charset=utf-8");
		response.getWriter().print(jArr);
		
		return null;
	} //execute()

} //AvailableAction
