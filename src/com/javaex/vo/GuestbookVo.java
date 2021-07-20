package com.javaex.vo;

public class GuestbookVo {

	// Field
	private int no;
	private String name;
	private String password;
	private String content;
	private String reg_date;

	// Constructor
	public GuestbookVo() {

	}
	
	public GuestbookVo(int no, String password) {
		this.no = no;
		this.password = password;
	}

	public GuestbookVo(String name, String password, String content) {
		this.name = name;
		this.password = password;
		this.content = content;
	}
	
	public GuestbookVo(int no, String name, String password, String content, String reg_date) {
		this.no = no;
		this.name = name;
		this.password = password;
		this.content = content;
		this.reg_date = reg_date;
	}

	// Method - G/S

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	// Method - Ordinary

	@Override
	public String toString() {
		return "GuestbookVo [no=" + no + ", name=" + name + ", password=" + password + ", content="
				+ content + ", reg_date=" + reg_date + "]";
	}

}