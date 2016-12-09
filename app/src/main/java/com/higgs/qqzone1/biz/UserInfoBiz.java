package com.higgs.qqzone1.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.higgs.qqzone1.common.SysConfig;
import com.higgs.qqzone1.dao.UserInfoDao;
import com.higgs.qqzone1.dao.impl.UserInfoDaoImpl;
import com.higgs.qqzone1.model.UserInfo;

public class UserInfoBiz {
	private static UserInfoDao oDao = new UserInfoDaoImpl();
	public static boolean userLogin(UserInfo oInfo) {
		return oDao.userLogin(oInfo);
	}

	public static void getLoginUserInfo() {
		SysConfig.loginUserInfo = oDao.getLoginUserInfo();
	}

	public static List<UserInfo> getUserInfos(String selectString, int page,
			int pagesize) {
		List<UserInfo> oInfos = oDao.getUserInfos(selectString, page, pagesize);
		if (oInfos != null && oInfos.size() > 0) {
			for (int i = 0; i < oInfos.size(); i++) {
				if (oInfos.get(i).getId() == SysConfig.loginUserInfo.getId()) {
					oInfos.remove(i);
				}

			}
		}
		return oInfos;
	}
	public static boolean userRegister(UserInfo oInfo) {
		return oDao.userRegister(oInfo);
		
	}
	/**
	 * 将返回的List<UserInfo>集合，转换成Map<String,Object>格式.用于系统适配器绑定值
	 * 
	 * @param oInfos
	 * @return
	 */
	public static List<Map<String, Object>> getUserListByBind(
			List<UserInfo> oInfos) {
		List<Map<String, Object>> dataList = null;
		// 如果传入的集合不为空
		if (oInfos != null && oInfos.size() > 0) {
			// 实例化绑定集
			dataList = new ArrayList<Map<String, Object>>();
			Map<String, Object> oMap = null;
			// 循环将userinfo转换成Map 并添加到集合中。每个用户对应一个Map
			for (UserInfo userInfo : oInfos) {
				oMap = new HashMap<String, Object>();
				oMap.put("username", userInfo.getNikeName());
				// oMap.put("userid", userInfo.getId());
				oMap.put("sex", userInfo.getSex());
				oMap.put("registertime", userInfo.getRegisterTime());
				dataList.add(oMap);
			}

		}
		return dataList;
	}
}
