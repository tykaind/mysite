package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	// Field
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "webdb";
	String pw = "webdb";

	// Get Connection
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

	// Get Close
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

	// SelectAll 리스트
	public List<BoardVo> getList() {

		// DB에서 리스트 가져옴
		List<BoardVo> arrayList = new ArrayList<BoardVo>();

		getConnection();

		try {

			String query = "";
			query += " select no, ";
			query += " 		  b_no, ";
			query += " 		  title, ";
			query += " 		  content, ";
			query += " 		  hit, ";
			query += "		  reg_date, ";
			query += " 		  user_no, ";
			query += " 		  name ";
			query += " from users u, board b ";
			query += " where u.no = b.user_no ";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				int b_no = rs.getInt("b_no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");
				String name = rs.getString("name");

				BoardVo boardVo = new BoardVo(no, b_no, title, content, hit, reg_date, user_no, name);

				arrayList.add(boardVo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		close();

		return arrayList;

	}

	// Insert 등록
	public int boardInsert(BoardVo boardVo) {
		int count = -1;
		getConnection();

		try {
			String query = "";
			query += " insert into board ";
			query += " values(seq_b_no.nextval, ?, ?, 0, sysdate, ?) ";

			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUser_no());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		close();
		return count;
	}

	// Search 기능
	public List<BoardVo> boardSearch(String search) {

		// DB에서 리스트 가져옴
		List<BoardVo> arrayList = new ArrayList<BoardVo>();

		getConnection();

		try {

			String query = "";
			query += " select b_no, ";
			query += "		  no, ";
			query += "        title, ";
			query += "        content, ";
			query += "        hit, ";
			query += "        reg_date, ";
			query += "        user_no, ";
			query += "        name ";
			query += " From users us left outer join board bo on us.no = bo.user_no ";
			query += " where name like '%" + search + "%'";
			query += " or title like '%" + search + "%'";
			query += " or content like '%" + search + "%'";
			query += " or b_no like '%" + search + "%'";
			query += " order by b_no desc";
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int b_no = rs.getInt("b_no");
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");
				String name = rs.getString("name");

				BoardVo boardVo = new BoardVo(no, b_no, title, content, hit, reg_date, user_no, name);

				arrayList.add(boardVo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return arrayList;
	}
	
	
	// SelectOne 기능
	public BoardVo getBoardInfo(int no) {

		BoardVo boardVo = null;
		getConnection();

		try {
			String query = "";
			query += " select b_no, ";
			query += " 		  title, ";
			query += " 		  content, ";
			query += " 		  hit, ";
			query += "		  reg_date, ";
			query += "		  user_no, ";
			query += " 		  name ";
			query += " from users u, board b ";
			query += " where u.no = b.user_no ";
			query += " and b_no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				int b_no = rs.getInt("b_no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");
				String name = rs.getString("name");

				boardVo = new BoardVo(b_no, title, content, hit, reg_date, user_no, name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		close();
		return boardVo;

	}
	
	//Delete
	public int boardDelete(int no) {
		
		int count = 0;
		getConnection();
		
		try {
			String query = "";
			query += " delete from board ";
			query += " where b_no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();
			System.out.println(count + "건이 삭제되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return count;
	}
	
	//Modify
	public int boardUpdate(BoardVo boardVo) {
		int count = -1;
		getConnection();
		
		try {
			String query = "";
			query += " update board ";
			query += " set title = ?, ";
			query += "     content = ? ";
			query += " where b_no = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getB_no());

			count = pstmt.executeUpdate();
			System.out.println(count + "업데이트 되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
		return count;
	}
	
	//Add Hit
	public void addHit(int no) {
		
		getConnection();
		
		try {
			String query = "";
			query += " Update board ";
			query += " set hit = hit + 1 ";
			query += " where b_no = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, no);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close();
	}
	
}