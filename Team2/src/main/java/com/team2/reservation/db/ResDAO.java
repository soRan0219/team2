package com.team2.reservation.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.team2.admin.db.ParkingDTO;
import com.team2.admin.db.ResDTO;
import com.team2.parkingdetail.db.PDetailDTO;

public class ResDAO {
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private String sql = "";
	private ResultSet rs = null;
	
	public Connection getCon() throws Exception {
		Context initCTX = new InitialContext();
		DataSource ds = (DataSource)initCTX.lookup("java:comp/env/jdbc/class7_230118_team2");
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
			
			sql = "SELECT parkingName,parkingAdr,inOutDoor,parkingTel FROM parking WHERE parkingCode=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, parkingCode);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//주차장 DB에 있음
				pDto.setParkingCode(parkingCode);
				pDto.setInOutDoor(rs.getString("inOutDoor"));
				pDto.setParkingAdr(rs.getString("parkingAdr"));
				pDto.setParkingName(rs.getString("parkingName"));
				pDto.setParkingTel(rs.getString("parkingTel"));
				
				System.out.println("DAO: 주차장 정보 저장 완료");
			} else {
				//주차장 DB에 없음
				System.out.println("DAO: 주차장 정보 DB에 없음");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		} //try
		
		return pDto;
	} //getParking()
	
	//주차장 모든 자리
	public List<PDetailDTO> getAllParkingDetail(String ParkingCode) {
		List<PDetailDTO> allList = new ArrayList<>(); 
		
		try {
			con = getCon();
			
			sql = "SELECT * FROM parkingDetail WHERE parkingCode=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ParkingCode);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PDetailDTO pDto = new PDetailDTO();
				pDto.setParkingCode(ParkingCode);
				pDto.setParkingPosition(rs.getInt("parkingPosition"));
				allList.add(pDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		} //try
		
		return allList;
	} //getAllParkingDetail
	
	//예약 가능한 자리 조회
	public List<PDetailDTO> getAvailable(ResDTO rDto) {
		List<PDetailDTO> available = new ArrayList<>();
		
		try {
			con = getCon();
			
			sql = "SELECT parkingCode, parkingPosition "
					+ " FROM parkingDetail "
					+ " WHERE parkingCode=? "
					+ " AND parkingPosition NOT IN "
					+ " (SELECT parkingPosition "
					+ " FROM reservation "
					+ " WHERE parkingCode=? "
					+ " AND resDate=?"
					+ " AND (( parkInTime>? AND parkInTime<? ) "
					+ " OR (parkOutTime>? AND parkOutTime<?) "
					+ " ))";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, rDto.getParkingCode());
			pstmt.setString(2, rDto.getParkingCode());
			pstmt.setDate(3, rDto.getResDate());
			
			
			pstmt.setTime(4, rDto.getParkInTime());
			pstmt.setTime(5, rDto.getParkOutTime());
			pstmt.setTime(6, rDto.getParkInTime());
			pstmt.setTime(7, rDto.getParkOutTime());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				PDetailDTO pdDto = new PDetailDTO();
				
				pdDto.setParkingCode(rs.getString(1));
				pdDto.setParkingPosition(rs.getInt(2));
				
				available.add(pdDto);
			} //while
			
			System.out.println("DAO: 예약 가능한 자리 조회 완료 - " + available.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		} //try
		
		return available;
	} //getAvailable()
	
	//이용시간에 따른 예상금액
	public int getPrice(Time parkInTime, Time parkOutTime) {
		int result = 0;
		
		try {
			con = getCon();
			
			sql = "SELECT "
					+ " (time_to_sec(timediff(?,?))/(30*60))*1000";
			pstmt = con.prepareStatement(sql);
			pstmt.setTime(1, parkOutTime);
			pstmt.setTime(2, parkInTime);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
			System.out.println("DAO: 예상 결제 금액 - " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return result;
	} //getPrice()
	
	
	
	
	
	
} //resDAO

