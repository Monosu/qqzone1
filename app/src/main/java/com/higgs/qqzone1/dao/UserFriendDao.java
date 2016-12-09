package com.higgs.qqzone1.dao;

import java.util.List;

import com.higgs.qqzone1.model.UserFriend;

public interface UserFriendDao {
	public boolean addUserFriend(UserFriend oFriend);

	public boolean deleteFriend(UserFriend oFriend);

	public List<UserFriend> getUserFriends(int userid);
}
