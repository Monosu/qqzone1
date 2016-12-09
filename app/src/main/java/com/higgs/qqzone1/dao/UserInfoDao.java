package com.higgs.qqzone1.dao;

import java.util.List;

import com.higgs.qqzone1.model.UserInfo;

public interface UserInfoDao {
	public boolean userLogin(UserInfo oInfo);
	public String updatepsd(UserInfo oInfo);
	public UserInfo getLoginUserInfo();
	public boolean userRegister(UserInfo oInfo);
	/**
	 * 查询所有用户信息
	 * 
	 * @param selectString
	 *            搜索的用户昵称如果是第一次加载为""
	 * @param page
	 *            要请求第几页
	 * @param pagesize
	 *            每页几条信息
	 * @return
	 */
	public List<UserInfo> getUserInfos(String selectString, int page,
			int pagesize);
}
