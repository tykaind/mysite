package com.javaex.dao;

import com.javaex.vo.UserVo;

public class DaoTest {

	public static void main(String[] args) {
		/*
		 * UserVo userVo = new UserVo("aaa","1234","박수현","male");
		 * 
		 * UserDao userDao = new UserDao(); userDao.userInsert(userVo);
		 */

		UserDao userDao = new UserDao();
		UserVo userVo = userDao.getUser("aaa", "1234");
		System.out.println(userVo);
		
	}

}
