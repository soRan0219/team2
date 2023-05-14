package com.team2.reservation.action;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.team2.admin.db.ResDTO;
import com.team2.commons.Action;
import com.team2.commons.ActionForward;
import com.team2.reservation.db.PayDTO;
import com.team2.reservation.db.ResDAO;

public class PayAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("M: PayAction_execute()");
		
		request.setCharacterEncoding("UTF-8");
		
		//아이디, 주차장코드, 자리번호
		String id = request.getParameter("id");
		String parkingCode = request.getParameter("parkingCode");
		int parkingPosition = Integer.parseInt(request.getParameter("parkingPosition"));
		
		//예약날짜
		String dateString = request.getParameter("resDate");
		Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		java.sql.Date resDate = new java.sql.Date(parsedDate.getTime());
		
		//입, 출차시간
		String fromTime = request.getParameter("parkInTime");
		String toTime = request.getParameter("parkOutTime");
		
		Time parkInTime = Time.valueOf(fromTime);
		Time parkOutTime = Time.valueOf(toTime);
		
		//연락처, 차량번호, 결제예상금액
		String contact = request.getParameter("tel");
		String carNo = request.getParameter("carNo");
		int price = Integer.parseInt(request.getParameter("price"));
		
		//위 정보 모두 ResDTO에 저장
		ResDTO dto = new ResDTO();
		dto.setId(id);
		dto.setParkingCode(parkingCode);
		dto.setParkingPosition(parkingPosition);
		dto.setResDate(resDate);
		dto.setParkInTime(parkInTime);
		dto.setParkOutTime(parkOutTime);
		dto.setPrice(price);
		dto.setCarNo(carNo);
		
		//결제 테이블에 들어갈 정보
		String payNo = request.getParameter("payNo");
		
		String strPayDate = request.getParameter("payDate");
		Date parsedPayDate = new SimpleDateFormat("yyyy-MM-dd").parse(strPayDate);
		java.sql.Date payDate = new java.sql.Date(parsedPayDate.getTime());
		
		//결제 정보 PayDTO에 저장
		PayDTO payDto = new PayDTO();
		payDto.setPayNo(payNo);
		payDto.setPayWay("card");
		payDto.setPayCondition("paid");
		payDto.setPayDate(payDate);
		payDto.setTotalPrice(price);
		
		ResDAO dao = new ResDAO();
		int resResult = dao.reservate(dto);
		int payResult = dao.pay(payDto);
		
		System.out.println("resResult: " + resResult + "\npayResult: " + payResult);
		
		JsonObject obj = new JsonObject();
		obj.addProperty("resResult", resResult);
		obj.addProperty("payResult", payResult);
		
		response.setContentType("application/x-json; charset=utf-8");
		response.getWriter().print(obj);
		
		return null;
	} //execute()

} //PayAction
