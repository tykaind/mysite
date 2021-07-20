package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		System.out.println(action);

		// Action If-문
		if ("addList".equals(action)) {
			System.out.println("[등록폼]");

			GuestbookDao guestbookDao = new GuestbookDao();
			List<GuestbookVo> guestList = guestbookDao.getList();

			request.setAttribute("guestList", guestList);

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");

		} else if ("add".equals(action)) {
			System.out.println("[등록]");

			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			GuestbookVo guestbookVo = new GuestbookVo(name, password, content);
			GuestbookDao guestbookDao = new GuestbookDao();
			guestbookDao.guestbookInsert(guestbookVo);

			WebUtil.redirect(request, response, "./gbc?action=addList");
		} else if ("dform".equals(action)) {
			System.out.println("[삭제폼]");

			int no = Integer.parseInt(request.getParameter("no"));

			request.setAttribute("no", no);

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");

		} else if ("delete".equals(action)) {

			System.out.println("[삭제]");
			String password = request.getParameter("password");
			int guestbookNo = Integer.parseInt(request.getParameter("no"));

			GuestbookDao guestbookDao = new GuestbookDao();
			GuestbookVo deletepw = new GuestbookVo(guestbookNo, password);

			guestbookDao.guestbookDelete(deletepw);

			WebUtil.redirect(request, response, "./gbc?action=addList");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}