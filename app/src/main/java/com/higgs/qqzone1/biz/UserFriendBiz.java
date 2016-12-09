package com.higgs.qqzone1.biz;

import java.util.List;

import com.higgs.qqzone1.dao.UserFriendDao;
import com.higgs.qqzone1.dao.impl.UserFriendDaoImpl;
import com.higgs.qqzone1.model.UserFriend;

public class UserFriendBiz {
	private static UserFriendDao oFriendDao = new UserFriendDaoImpl();

	public static boolean addUserFriend(UserFriend oFriend) {
		return oFriendDao.addUserFriend(oFriend);
	}

	public static boolean deleteUserFriend(UserFriend oFriend) {
		return oFriendDao.deleteFriend(oFriend);
	}

	public static List<UserFriend> getFriends(int userid) {
		return oFriendDao.getUserFriends(userid);
	}
}
