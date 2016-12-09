package com.higgs.qqzone1.model;

import java.util.Date;

public class UserInfo {
	private int id;
	private String loginName;
	private String passWord;
	private Date lastTime;
	private Date registerTime;
	private String sex;
	private String nikeName;
	private int isLogin;

	/**
	 * @return isLogin
	 */
	public int getIsLogin() {
		return isLogin;
	}

	/**
	 * @param isLogin
	 *            要设置的 isLogin
	 */
	public void setIsLogin(int isLogin) {
		this.isLogin = isLogin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNikeName() {
		return nikeName;
	}
	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	
}
