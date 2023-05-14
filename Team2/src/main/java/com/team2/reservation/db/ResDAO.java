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
	
	//예약 가능한 자리 조회
	public List<PDetailDTO> getAvailable(ResDTO rDto) {
		List<PDetailDTO> available = new ArrayList<>();
		
		try {
			con = getCon();
			
			sql = "SELECT parkingCode, parkingPosition "
					+ " FROM parkingDetail "
					+ " WHERE parkingCode=? "
					+ " AND (parkingPosition NOT IN "
					+ " (SELECT parkingPosition "
					+ " FROM reservation "
					+ " WHERE parkingCode=? "
					+ " AND resDate=?"
					+ " AND ( parkInTime<? AND parkOutTime>? ) "
					+ " AND resStatus=1) "
					+ " OR ( "
					+ " parkingPosition IN ( "
					+ " SELECT parkingPosition "
					+ " FROM reservation "
					+ " WHERE parkingCode=? "
					+ " AND resDate=? "
					+ " AND (parkInTime<? AND parkOutTime>?) "
					+ " AND resStatus=0 )) "
					+ " )";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, rDto.getParkingCode());
			pstmt.setString(2, rDto.getParkingCode());
			pstmt.setDate(3, rDto.getResDate());
			pstmt.setTime(4, rDto.getParkOutTime());
			pstmt.setTime(5, rDto.getParkInTime());
			
			pstmt.setString(6, rDto.getParkingCode());
			pstmt.setDate(7, rDto.getResDate());
			pstmt.setTime(8, rDto.getParkOutTime());
			pstmt.setTime(9, rDto.getParkInTime());
			
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
	
	//예약정보 insert
	public int reservate(ResDTO dto) {
		int result = 0;
		
		try {
			con = getCon();
			
			sql = "INSERT INTO reservation (id,parkingCode,parkingPosition,resDate,parkInTime,parkOutTime,price,carNo) "
					+ " VALUES (?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getParkingCode());
			pstmt.setInt(3, dto.getParkingPosition());
			pstmt.setDate(4, dto.getResDate());
			pstmt.setTime(5, dto.getParkInTime());
			pstmt.setTime(6, dto.getParkOutTime());
			pstmt.setInt(7, dto.getPrice());
			pstmt.setString(8, dto.getCarNo());
			
			result = pstmt.executeUpdate();
			
			System.out.println("DAO: 예약 테이블에 정보 저장 - " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return result;
	} //reservate()
	
	//결제정보 insert
	public int pay(PayDTO dto) {
		int result = 0;
		
		try {
			con = getCon();
			
			sql = "INSERT INTO pay VALUES(?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getPayNo());
			pstmt.setString(2, dto.getPayWay());
			pstmt.setString(3, dto.getPayCondition());
			pstmt.setDate(4, dto.getPayDate());
			pstmt.setInt(5, dto.getTotalPrice());
			
			result = pstmt.executeUpdate();
			
			System.out.println("DAO: 결제 테이블에 정보 저장 - " + result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return result;
	} //pay()
	
	
} //resDAO

