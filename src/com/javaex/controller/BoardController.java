package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/bc")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		BoardDao boardDao = new BoardDao();

		String action = request.getParameter("action");

		if ("list".equals(action)) {
			System.out.println("[리스트]");

			List<BoardVo> boardList = boardDao.getList();

			request.setAttribute("boardList", boardList);

			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

		} else if ("delete".equals(action)) {
			System.out.println("[삭제]");
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println(no);
			
			boardDao.boardDelete(no);

			WebUtil.redirect(request, response, "./bc?action=list");

		} else if ("writeform".equals(action)) {
			System.out.println("[수정폼]");

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			if (authUser != null) {
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			} else {
				WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			}

		} else if ("write".equals(action)) {
			System.out.println("[수정]");

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int user_no = authUser.getNo();

			BoardVo boardVo = new BoardVo(title, content, user_no);

			boardDao.boardInsert(boardVo);

			WebUtil.redirect(request, response, "./bc?action=list");
		} else if ("read".equals(action)) {
			System.out.println("[게시물]");

			int no = Integer.parseInt(request.getParameter("no"));

			boardDao.addHit(no);

			BoardVo boardVo = boardDao.getBoardInfo(no);

			request.setAttribute("readBoardVo", boardVo);

			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");

		} else if ("modifyform".equals(action)) {
			System.out.println("[게시물수정폼]");
			int no = Integer.parseInt(request.getParameter("no"));

			BoardVo boardVo = boardDao.getBoardInfo(no);

			request.setAttribute("modifyBoardVo", boardVo);

			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		} else if ("modify".equals(action)) {
			System.out.println("[게시물수정폼]");

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int b_no = Integer.parseInt(request.getParameter("no"));
			int userno = Integer.parseInt(request.getParameter("userno"));

			if (authUser.getNo() == userno) {
				BoardVo boardVo = new BoardVo(b_no, title, content);

				boardDao.boardUpdate(boardVo);

				WebUtil.redirect(request, response, "./bc?action=list");
			} else {
				WebUtil.redirect(request, response, "./bc?action=list");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}