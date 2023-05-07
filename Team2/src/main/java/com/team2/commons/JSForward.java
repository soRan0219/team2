package com.team2.commons;
//
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class JSForward {
	// 자바스크립트 사용해서 페이지 이동
	
	// 1. alert 메세지 출력, 페이지 뒤로가기
	public static void alertAndBack(HttpServletResponse response
													, String msg){ // 메서드 만들기
		// response 자바에서 사용 X, 파라미터 값을 줘서 사용하기

		
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();		
			out.println("<script>");
			out.println("alert('"+msg+"!');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	} //alertAndBack()
	
	// 2. alert 메세지 출력, 원하는 위치로 이동
	public static void alertAndMove(HttpServletResponse response, 
											String msg, String location) {
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('"+msg+"');");
			out.println("location.href='"+location+"';");
			out.println("</script>");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	} //alertAndMove()
	
	//3. confirmAndMove()
	public static void confirmAndMove(HttpServletResponse response, String msg, String yesLoc, String noLoc) {
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("var c=confirm('"+ msg +"');");
			out.print("if(c){");
			out.print("location.href='"+ yesLoc +"';");
			out.print("}else{");
			out.print("location.href='"+ noLoc +"';");
			out.print("}");
			out.print("</script>");
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	} //confirmAndMove()
	

}
