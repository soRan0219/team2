package com.team2.reservation.action;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team2.admin.db.ResDTO;
import com.team2.commons.Action;
import com.team2.commons.ActionForward;
import com.team2.commons.JSForward;
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
		System.out.println(price);
		
		//위 정보 모두 저장
		ResDTO dto = new ResDTO();
		dto.setId(id);
		dto.setParkingCode(parkingCode);
		dto.setParkingPosition(parkingPosition);
		dto.setResDate(resDate);
		dto.setParkInTime(parkInTime);
		dto.setParkOutTime(parkOutTime);
		dto.setPrice(price);
		dto.setCarNo(carNo);
		
		ResDAO dao = new ResDAO();
		int result = dao.reservate(dto);
		
		if(result==0) {
			JSForward.alertAndBack(response, "결제에 실패했습니다.");
		} else {
			JSForward.confirmAndMove(response, "결제성공! 예약내역 페이지로 이동하시겠습니까?", "./ResInfo.park", "./Main.park");
		}
		
		return null;
	} //execute()

} //PayAction
