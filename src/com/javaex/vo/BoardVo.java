package com.javaex.vo;

public class BoardVo {
	
	//Field
	private int no;
	private int b_no;
	private String title;
	private String  content;
	private int hit;
	private String reg_date;
	private int user_no;
	private String name;
	
	//Constructor

	public BoardVo() {

	}
	
	public BoardVo(int no, int b_no) {
		this.no = no;
		this.b_no = b_no;
	}
	
	public BoardVo(String title, String content, int user_no) {
		this.title = title;
		this.content = content;
		this.user_no = user_no;
	}

	public BoardVo(int b_no, String title, String content) {
		this.b_no = b_no;
		this.title = title;
		this.content = content;
	}
	
	public BoardVo(int b_no, String title, String content, int hit, String reg_date, int user_no) {
		this.b_no = b_no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.reg_date = reg_date;
		this.user_no = user_no;
	}
	
	public BoardVo(int no, int b_no, String title, String content, int hit, String reg_date, int user_no,
			String name) {
		this.no = no;
		this.b_no = b_no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.reg_date = reg_date;
		this.user_no = user_no;
		this.name = name;
	}

	public BoardVo(int b_no, String title, String content, int hit, String reg_date, int user_no, String name) {
		this.b_no = b_no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.reg_date = reg_date;
		this.user_no = user_no;
		this.name = name;
	}

	//Method - G/S
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getHit() {
		return hit;
	}
	
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	public String getReg_date() {
		return reg_date;
	}
	
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	public int getUser_no() {
		return user_no;
	}
	
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getNo() {
		return no;
	}
	
	public void setNo(int no) {
		this.no = no;
	}
	
	public int getB_no() {
		return b_no;
	}
	
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}

	//Method - Ordinary
	
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", b_no=" + b_no + ", title=" + title + ", content=" + content + ", hit="
				+ hit + ", reg_date=" + reg_date + ", user_no=" + user_no + ", name=" + name + "]";
	}
	

}