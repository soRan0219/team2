package com.team2.reservation.action;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.team2.commons.Action;
import com.team2.commons.ActionForward;
import com.team2.parkingdetail.db.PDetailDTO;
import com.team2.reservation.db.ResDAO;
import com.team2.reservation.db.ResDTO;

public class AvailableAction implements Action {
	
	public Time stringToTime(String time) {
		Time parsedTime = null;
		try {
			String sTime = time.split(" ")[4];
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
			Date parsedDate = dateFormat.parse(sTime);
			parsedTime = new Time(parsedDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return parsedTime;
	} //stringToTime()

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M: AvailableAction_execute()");
		
		request.setCharacterEncoding("utf-8");
		
		//희망 예약 날짜 
		String dateString = request.getParameter("date");
//		System.out.println(dateString);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z",Locale.ENGLISH);
		Date parsedDate = dateFormat.parse(dateString);
		java.sql.Date resDate = new java.sql.Date(parsedDate.getTime());
//		System.out.println(resDate);
		
		//희망 입,출차 시간 
		String fromTime = request.getParameter("fromTime");
		String toTime = request.getParameter("toTime");
		Time parkInTime = stringToTime(fromTime);
		Time parkOutTime = stringToTime(toTime);
//		System.out.println(parkInTime);
//		System.out.println(parkOutTime);
		
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
		System.out.println("price: " + price);
		List<PDetailDTO> aList = dao.getAvailable(rDto);
		
		JSONArray jArr = new JSONArray();
		
		Iterator it = aList.iterator();
		if(it.hasNext()) {
			for(int i=0; i<aList.size(); i++) {
				JSONObject jobj = new JSONObject();
				jobj.put("parkingCode", aList.get(i).getParkingCode());
				jobj.put("parkingPosition", aList.get(i).getParkingPosition());
				jArr.add(jobj);
			}
		}
		JSONObject jobj = new JSONObject();
		jobj.put("price", price);
		jArr.add(jobj);
		
		System.out.println(jArr.size());
		
		response.setContentType("application/x-json; charset=utf-8");
		response.getWriter().print(jArr);
		
		return null;
	} //execute()

} //AvailableAction
