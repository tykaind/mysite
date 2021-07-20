package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "webdb";
	String pw = "webdb";

	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void close() {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	public int userInsert(UserVo userVo) {
		int count = -1;
		getConnection();

		try {
			String query = "";
			query += " insert into users ";
			query += " values( seq_user_no.nextval, ?, ?, ?, ? ) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPw());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

			count = pstmt.executeUpdate();
			System.out.println(count + "건이 등록되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return count;
	}

	public int userUpdate(UserVo userVo) {
		int count = -1;
		getConnection();

		try {
			String query = "";
			query += " update users ";
			query += " set password = ?, ";
			query += "     name = ?, ";
			query += "     gender = ? ";
			query += " where no = ?	 ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getPw());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());

			count = pstmt.executeUpdate();
			System.out.println(count + "건이 등록되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return count;
	}

	public UserVo getUser(String id, String pw) {

		UserVo userVo = null;
		getConnection();

		try {
			String query = "";
			query += " select no, name";
			query += " from users ";
			query += " where id = ? ";
			query += " and password = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");

				userVo = new UserVo();
				userVo.setNo(no);
				userVo.setName(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return userVo;
	}

	public UserVo getUserInfo(int userNo) {

		UserVo userVo = null;
		getConnection();

		try {
			String query = "";
			query += " select no, id, password, name, gender";
			query += " from users ";
			query += " where no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userNo);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				int no = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");

				userVo = new UserVo(no, id, password, name, gender);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return userVo;
	}

}