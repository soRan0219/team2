package com.team2.reservation.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.team2.parking.db.ParkingDTO;

public class resDAO {
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private String sql = "";
	private ResultSet rs = null;
	
	public Connection getCon() throws Exception {
		Context initCTX = new InitialContext();
		DataSource ds = (DataSource)initCTX.lookup("java:comp/env/jdbc/team2");
		con = ds.getConnection();
		
		System.out.println("DAO: DB 연결 성공 " + con);
		
		return con;
	} //getCon()
	
	public void closeDB() {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	} //closeDB()

	//주차장 정보 조회
	public ParkingDTO getParking(String parkingCode) {
		ParkingDTO pDto = new ParkingDTO();
		
		try {
			con = getCon();
			
			sql = "SELECT parkingName,parkingAdr,inOutDoor FROM parking WHERE parkingCode=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, parkingCode);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//주차장 DB에 있음
				pDto.setInOutDoor(rs.getString("inOutDoor"));
				pDto.setParkingAdr(rs.getString("parkingAdr"));
				pDto.setParkingName(rs.getString("parkingName"));
				
				System.out.println("DAO: 주차장 정보 저장 완료");
			} else {
				//주차장 DB에 없음
				System.out.println("DAO: 주차장 정보 DB에 없음");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} //try
		
		return pDto;
	} //getParking()
	
	
	
	
	
	
	
} //resDAO

