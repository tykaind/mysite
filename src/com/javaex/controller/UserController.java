package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		if ("joinForm".equals(action)) {
			System.out.println("[회원가입폼]");

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		} else if ("join".equals(action)) {
			System.out.println("[회원가입]");

			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			UserVo userVo = new UserVo(id, pw, name, gender);
			System.out.println(userVo);

			UserDao userDao = new UserDao();
			int count = userDao.userInsert(userVo);

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");

		} else if ("loginForm".equals(action)) {
			System.out.println("[로그인폼]");

			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		} else if ("login".equals(action)) {
			System.out.println("[로그인]");

			String id = request.getParameter("id");
			String pw = request.getParameter("pw");

			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(id, pw);

			if (userVo != null) {
				System.out.println("로그인 성공");

				HttpSession session = request.getSession();
				session.setAttribute("authUser", userVo);

				WebUtil.redirect(request, response, "./main");
			} else {
				System.out.println("로그인 실패");

				WebUtil.redirect(request, response, "./user?action=loginForm&result=fail");

			}

		} else if ("logout".equals(action)) {
			System.out.println("[로그아웃]");

			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();

			WebUtil.redirect(request, response, "./main");
		} else if ("modifyForm".equals(action)) {

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			if (authUser != null) {
				UserDao userDao = new UserDao();
				UserVo userVo = userDao.getUserInfo(authUser.getNo());
				request.setAttribute("userVo", userVo);

				WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			} else {
				WebUtil.redirect(request, response, "./user?action=loginForm");
			}
		} else if ("update".equals(action)) {

			UserDao userDao = new UserDao();
			int no = Integer.parseInt(request.getParameter("no"));
			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			UserVo userVo = new UserVo(no, password, name, gender);
			userDao.userUpdate(userVo);

			HttpSession session = request.getSession();

			((UserVo) session.getAttribute("authUser")).setName(name);

			WebUtil.redirect(request, response, "./main");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}