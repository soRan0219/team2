package com.team2.admin.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

// DB에 연결해서 처리하는 모든동작 수행
public class MemberDAO {
	// 공통변수 선언
	private Connection con = null;  		//디비 연결정보 저장(관리)
	private PreparedStatement pstmt = null; //SQL 작성, 실행
	private ResultSet rs = null; 			//select결과 데이터 저장
	private String sql ="";					//SQL구문 저장

	//디비 연결 메서드
	private Connection getCon() throws Exception{
		// Context 객체생성
		Context initCTX = new InitialContext();
		// 디비연결정보 불러와서 사용
		DataSource ds 
		   = (DataSource) initCTX.lookup("java:comp/env/jdbc/class7_230118_team2");
		// 디비연결
		con = ds.getConnection();
		System.out.println(" DAO : 디비연결 성공! "+con);
		
		return con;
	}
	
	// 자원해제
	public void closeDB() {
		try {
			if(rs != null)  rs.close();
			if(pstmt != null)  pstmt.close();
			if(con != null)  con.close();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 예약리스트만 들고옴
	public List<ResDTO> resList() {
		
		List<ResDTO> resList = new ArrayList<ResDTO>();
		
		try {
			// 1. 2. DB연결
			con = getCon();
			// 3. sql작성 & pstmt 객체
			sql = "SELECT * FROM reservation";
			pstmt = con.prepareStatement(sql);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 데이터가 있는 동안 반복해서 정보를 담음
			while(rs.next()) {
				ResDTO dto = new ResDTO();
				
				dto.setResNo(rs.getInt("resNo"));
				dto.setId(rs.getString("id"));
				dto.setResDate(rs.getDate("resDate"));
				dto.setParkInTime(rs.getTime("parkInTime"));
				dto.setParkOutTime(rs.getTime("parkOutTime"));
				dto.setParkingCode(rs.getString("parkingCode"));
				dto.setResStatus(rs.getInt("resStatus"));
				
				resList.add(dto);
			}
			
			System.out.println("예약관리");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		
		return resList;
	} // 예약리스트
	
	
	
	
	
	// 관리자 회원리스트
	public List<MemberDTO> memberList() {
		
		List<MemberDTO> memberList = new ArrayList<MemberDTO>();
		
		try {
			// 1. 2. DB연결
			con = getCon();
			// 3. sql작성 & pstmt 객체
			sql = "SELECT m.id, memName, birth, tel, email, count(resStatus), point, regDate"
					+ " FROM member m"
					+ " JOIN reservation r on r.id = m.id WHERE resStatus =1 GROUP BY m.id;"; // sql문 띄어쓰기 !!
			pstmt = con.prepareStatement(sql);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			// 데이터가 있는 동안 반복해서 정보를 담음
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				
				dto.setId(rs.getString("id"));
				dto.setMemName(rs.getString("memName"));
				dto.setBirth(rs.getDate("birth"));
				dto.setTel(rs.getString("tel"));
				dto.setEmail(rs.getString("email"));
				dto.setPoint(rs.getInt("point"));
				dto.setResStatus(rs.getInt("count(resStatus)"));
				dto.setRegDate(rs.getDate("regDate"));
				
				memberList.add(dto);
				
			}
			
			System.out.println("예약관리");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		
		return memberList;
	}
	
	
	// 총 회원수 출력
	public MemberDTO memberListCount() {
		
		MemberDTO dto = null;
		
		try {
			// 1. 2. DB연결
			con = getCon();
			// 3. sql작성 & pstmt 객체
			sql = "SELECT count(id) FROM member";
			pstmt = con.prepareStatement(sql);
			
			// 4. sql 실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDTO();
				
				dto.setMemberCount(rs.getInt("count(id)"));
				
			}
			
			
			System.out.println("총 회원수 저장완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		
		
		return dto;
	}
	
	
	// 총 예약건수
	public ResDTO resCount() {
		ResDTO dto = null;
		
		try {
			// DB연결, sql 작성
			con = getCon();
			sql = "SELECT count(resStatus) FROM reservation WHERE resStatus=1";
			pstmt = con.prepareStatement(sql);
			
			// sql 실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ResDTO();
				
				dto.setResStatus(rs.getInt("count(resStatus)"));
			}
			
			
			System.out.println("총 예약건수 저장완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		
		return dto;
	} // 총 예약건수
	
	// 총 예약취소건수
	public ResDTO resCancelCount() {
		ResDTO dto = null;
		
		try {
			// DB연결, sql 작성
			con = getCon();
			sql = "SELECT count(resStatus) FROM reservation WHERE resStatus=0";
			pstmt = con.prepareStatement(sql);
			
			// sql 실행
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ResDTO();
				
				dto.setResStatus(rs.getInt("count(resStatus)"));
			}
			
			
			System.out.println("총 예약취소건수 저장완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		
		return dto;
	} // 총 예약취소건수
	
	
	// 주차장 정보(parking)
	public List<ParkingDTO> parkingInfo() {
		
		ParkingDTO dto = null;
		List<ParkingDTO> parkingInfoList = new ArrayList<ParkingDTO>();
		
		try {
			// DB연결, sql 작성
			con = getCon();
			sql = "SELECT * FROM parking";
			pstmt = con.prepareStatement(sql);
			
			// sql 실행
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dto = new ParkingDTO();
				
				dto.setParkingCode(rs.getString("parkingCode"));
				dto.setParkingName(rs.getString("parkingName"));
				dto.setParkingAdr(rs.getString("parkingAdr"));
				dto.setInOutDoor(rs.getString("inOutDoor"));
				dto.setParkingTotal(rs.getInt("parkingTotal"));
				dto.setParkingTel(rs.getString("parkingTel"));
				
				parkingInfoList.add(dto);
			}
			
			System.out.println("주차장 정보 저장완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		
		return parkingInfoList;
	} // 주차장 정보 저장
	
	

}//DAO
