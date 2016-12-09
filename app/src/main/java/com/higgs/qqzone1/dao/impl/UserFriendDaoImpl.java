package com.higgs.qqzone1.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.text.TextUtils;

import com.higgs.qqzone1.AppApplication;
import com.higgs.qqzone1.common.DomXml;
import com.higgs.qqzone1.common.HttpCommon;
import com.higgs.qqzone1.common.SysConfig;
import com.higgs.qqzone1.common.UtiyCommon;
import com.higgs.qqzone1.dao.UserFriendDao;
import com.higgs.qqzone1.model.UserFriend;

public class UserFriendDaoImpl implements UserFriendDao {
	String url = SysConfig.serverUrl + "UserFriendServer?";

	/**
	 * 添加好友
	 */
	public boolean addUserFriend(UserFriend oFriend) {
		// TODO 自动生成的方法存根
		String addUrl = url + "action=1&uid=" + oFriend.getUserId() + "&fid="
				+ oFriend.getFriendId() + "&rname=" + oFriend.getRemarkName();
		int errCount = 1;
		String errMsg = "添加失败";
		String alertMsg = "添加失败";
		boolean result = false;
		Document oDocument = DomXml.loadXml(HttpCommon.doGet(addUrl));
		if (oDocument != null) {
			Element oElement = oDocument.getDocumentElement();
			errCount = UtiyCommon.getParseInt(
					oElement.getAttribute("errcount"), -1);
			errMsg = oElement.getAttribute("errmsg");
			if (errCount == 0) {
				result = true;
				errMsg = "";
				alertMsg = "";
			} else {
				if (!TextUtils.isEmpty(errMsg)) {
					alertMsg = errMsg;
				}
			}


		}
		if (!TextUtils.isEmpty(alertMsg)) {
			AppApplication.toaskMessage(alertMsg);
		}

		return result;
	}

	/**
	 * 删除好友
	 */
	public boolean deleteFriend(UserFriend oFriend) {
		// TODO 自动生成的方法存根
		boolean result = false;
		int errCount = 1;
		String errMsg = "删除好友失败";
		String urlString = url + "action=2&fid=" + oFriend.getFriendId()
				+ "&uid=" + oFriend.getUserId();
		String alertMsg = "删除好友失败";
		Document oDocument = DomXml.loadXml(HttpCommon.doGet(urlString));
		if (oDocument != null) {
			Element oElement = oDocument.getDocumentElement();
			if (oElement!=null) {
				errCount = UtiyCommon.getParseInt(oElement.getAttribute("errcount"),-1);
				errMsg = oElement.getAttribute("errmsg");
				if (errCount == 0) {
					// errMsg="";
					result = true;
					alertMsg = "";
				} else {
					if (!TextUtils.isEmpty(errMsg)) {
						alertMsg = errMsg;
					}
				}
			}
		}
		if (!TextUtils.isEmpty(alertMsg)) {
			AppApplication.toaskMessage(alertMsg);
		}
		return result;
	}

	/**
	 * 根据用户id获取用户的好友
	 */
	public List<UserFriend> getUserFriends(int userid) {
		// TODO 自动生成的方法存根
		String urlString = url + "action=3&uid=" + userid;
		List<UserFriend> dataList = null;
		Document oDocument = DomXml.loadXml(HttpCommon.doGet(urlString));
		String alertMsg = "";
		if (oDocument != null) {
			Element oElement = oDocument.getDocumentElement();
			int errCount = UtiyCommon.getParseInt(oElement
.getAttribute("errcount"), -1);
			String errMsg = oElement.getAttribute("errmsg");
			if (errCount == 1) {
				alertMsg = errMsg;
			} else {
				int userCount = UtiyCommon.getParseInt(
						oElement.getAttribute("usercount"), -1);
				if (userCount > 0) {
					NodeList userFriendList = oElement.getChildNodes();
					if (userFriendList != null
							&& userFriendList.getLength() > 0) {
						dataList = new ArrayList<UserFriend>();
						for (int i = 0; i < userFriendList.getLength(); i++) {
							UserFriend oFriend = getUserFriendByXml(userFriendList
									.item(i));
							if (oFriend != null) {
								dataList.add(oFriend);
							}

						}
					}
				} else {
					alertMsg = "数据已经全部加载";
				}
			}
		}
		if (!TextUtils.isEmpty(alertMsg)) {
			AppApplication.toaskMessage(alertMsg);
		}
		return dataList;
	}

	private UserFriend getUserFriendByXml(Node oNode) {
		UserFriend oFriend = null;
		if (oNode != null && oNode.getNodeType() == Document.ELEMENT_NODE) {
			NodeList userFriendList = oNode.getChildNodes();
			if (userFriendList != null && userFriendList.getLength() > 0) {
				oFriend = new UserFriend();
				for (int i = 0; i < userFriendList.getLength(); i++) {
					Node friendNode = userFriendList.item(i);
					if (friendNode.getNodeName().equals("id")) {
						oFriend.setId(UtiyCommon.getParseInt(friendNode
								.getFirstChild().getNodeValue()));
					} else if (friendNode.getNodeName().equals("friendid")) {
						oFriend.setFriendId(UtiyCommon.getParseInt(friendNode
								.getFirstChild().getNodeValue()));
					} else if (friendNode.getNodeName().equals("userid")) {
						oFriend.setUserId(UtiyCommon.getParseInt(friendNode
								.getFirstChild().getNodeValue()));
					} else if (friendNode.getNodeName().equals("remarname")) {
						oFriend.setRemarkName(friendNode.getFirstChild()
								.getNodeValue());
					}
				}
			}
		}
		return oFriend;
	}

}
