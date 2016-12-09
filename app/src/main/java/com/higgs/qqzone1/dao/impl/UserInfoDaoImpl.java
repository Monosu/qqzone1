package com.higgs.qqzone1.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.higgs.qqzone1.dao.UserInfoDao;
import com.higgs.qqzone1.model.UserInfo;

/**
 * 用户操作表
 * 
 * @author Administrator
 * 
 */
public class UserInfoDaoImpl implements UserInfoDao {
	String url = "UserServer";

	/**
	 * 用户登陆方法
	 */
	public boolean userLogin(UserInfo oInfo) {
		// TODO Auto-generated method stub
		// 设置返回值
		boolean result = false;
		// 定义异常代码
		int errCode = 0;
		// 定义异常信息
		String errMsg = "";
		// 定义接收值
		UserInfo userInfo= null;
		// 定义如果错误需要弹出的消息。如果为""则不会弹出
		String errAlert = "登陆失败";
		// 获取提交地址
		String loginUrl = SysConfig.serverUrl+url+"?action=1";
		// 设置提交参数
		loginUrl+="&username="+oInfo.getLoginName()+"&password="+oInfo.getPassWord();
		// try {
		// loginUrl = URLEncoder.encode(loginUrl, "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// // TODO 自动生成的 catch 块
		// e.printStackTrace();
		// }
		// 使用get方式请求服务端。并获取返回值
		String resultString = HttpCommon.doGet(loginUrl);
		// 把返回的xml解析成Document对象
		Document oDocument = DomXml.loadXml(resultString);
		// 如果Document不为空则代表返回的是有效的XML文档。有正常请求到页面
		if (oDocument!=null) {
			// 获取根对象
			Element oElement = oDocument.getDocumentElement();
			// 判断根对象是否为空。是否是有效节点类型
			if (oElement!=null&&oElement.getNodeType()==Document.ELEMENT_NODE) {
				// 获取错误编码。0则是正常编码。所有这里取转换值。默认值必须为-1
				errCode = UtiyCommon.getParseInt(oElement.getAttribute("errcount"),-1);
				// 获取错误信息
				errMsg = oElement.getAttribute("errmsg");
				// 如果编码=0。代表请求成功。解析xml并把用户完整信息写入数据库
				if (errCode==0) {
					// 获取到userinfo节点
					NodeList oList = oElement.getChildNodes();
					if(oList!=null&&oList.getLength()>0){
						for (int i = 0; i < oList.getLength(); i++) {
							// 将Node节点读取成Userinfo对象
							userInfo = getUserInfoByXml(oList.item(i));
							// 如果正确读取到userinfo对象。则代表数据返回成功
							if (userInfo!=null) {
								// 清空错误提示码
								errAlert= "";
								// 设置返回登录成功
								result = true;
								// 设置用户为自动登陆用户
								userInfo.setIsLogin(1);
								// 将用户信息写入的sqlite数据库中。该用户为自动登陆用户
								insertUserInfo(userInfo);
								// 将当前用户赋值为系统配置中的loginUserInfo 存储起来
								SysConfig.loginUserInfo = userInfo;
							}
						}
					}
				}else {
					// 否则判断是否有返回异常信息。有则弹出
					if (!TextUtils.isEmpty(errMsg)) {
						errAlert = errMsg;
					}
					
				}
			}
		}
		// 如果有错误消息则调用消息弹出
		if (!TextUtils.isEmpty(errAlert)) {
			AppApplication.toaskMessage(errAlert);
		}
		return result;
	}

	public boolean userRegister(UserInfo oInfo) {
		// 设置返回值
		boolean result = false;
		// 定义异常代码
		int errCode = 0;
		// 定义异常信息
		String errMsg = "";
		// 定义接收值
		UserInfo userInfo = null;
		// 定义如果错误需要弹出的消息。如果为""则不会弹出
		String errAlert = "注册失败";
		// 获取提交地址
		String loginUrl = SysConfig.serverUrl + url + "?action=2";
		// 设置提交参数
		loginUrl += "&username=" + oInfo.getLoginName() + "&password="
				+ oInfo.getPassWord();
		// 使用get方式请求服务端。并获取返回值
		String resultString = HttpCommon.doGet(loginUrl);
		// 把返回的xml解析成Document对象
		Document oDocument = DomXml.loadXml(resultString);
		// 如果Document不为空则代表返回的是有效的XML文档。有正常请求到页面
		if (oDocument != null) {
			// 获取根对象
			Element oElement = oDocument.getDocumentElement();
			// 判断根对象是否为空。是否是有效节点类型
			if (oElement != null
					&& oElement.getNodeType() == Document.ELEMENT_NODE) {
				// 获取错误编码。0则是正常编码。所有这里取转换值。默认值必须为-1
				errCode = UtiyCommon.getParseInt(
						oElement.getAttribute("errcount"), -1);
				// 获取错误信息
				errMsg = oElement.getAttribute("errmsg");
				// 如果编码=0。代表请求成功。解析xml并把用户完整信息写入数据库
				if (errCode == 0) {
					// 获取到userinfo节点
					NodeList oList = oElement.getChildNodes();
					if (oList != null && oList.getLength() > 0) {
						for (int i = 0; i < oList.getLength(); i++) {
							// 将Node节点读取成Userinfo对象
							userInfo = getUserInfoByXml(oList.item(i));
							// 如果正确读取到userinfo对象。则代表数据返回成功
							if (userInfo != null) {
								// 清空错误提示码
								errAlert = "";
								// 设置返回登录成功
								result = true;
								// 设置用户为自动登陆用户
								userInfo.setIsLogin(1);
								// 将用户信息写入的sqlite数据库中。该用户为自动登陆用户
								insertUserInfo(userInfo);
								// 将当前用户赋值为系统配置中的loginUserInfo 存储起来
								SysConfig.loginUserInfo = userInfo;
							}
						}
					}
				} else {
					// 否则判断是否有返回异常信息。有则弹出
					if (!TextUtils.isEmpty(errMsg)) {
						errAlert = errMsg;
					}

				}
			}
		}
		// 如果有错误消息则调用消息弹出
		if (!TextUtils.isEmpty(errAlert)) {
			AppApplication.toaskMessage(errAlert);
		}
		return result;
	}

	/**
	 * 将用户信息添加到sqlite数据库中
	 * 
	 * @param oInfo
	 * @return
	 */
	public boolean insertUserInfo(UserInfo oInfo) {
		boolean result = false;
		if (oInfo != null) {
			// 添加前先删除id为本次添加的用户id。避免数据重复
			SysConfig.dbHelper.deleteModel("USERINFO", "id=?",
					new String[] { String.valueOf(oInfo.getId()) });
			// 添加数据。将userinfo对象转换成数据写入map集合。
			result = SysConfig.dbHelper
					.insertModel("USERINFO", getDBMap(oInfo));
		}
		return result;
	}

	/**
	 * 把指定的userinfo对象转换成数据map。用于插入数据库。map的key为列名。值为具体值
	 * 
	 * @param oInfo
	 * @return
	 */
	private Map<String, String> getDBMap(UserInfo oInfo) {
		Map<String, String> oMap = null;
		if (oInfo != null) {
			oMap = new HashMap<String, String>();
			oMap.put("id", String.valueOf(oInfo.getId()));
			oMap.put("loginname", oInfo.getLoginName());
			oMap.put("password", oInfo.getPassWord());
			oMap.put("lasttime", UtiyCommon.dataFormt(oInfo.getLastTime()));
			oMap.put("registertime",
					UtiyCommon.dataFormt(oInfo.getRegisterTime()));
			oMap.put("sex", oInfo.getSex());
			oMap.put("nikeName", oInfo.getNikeName());
			oMap.put("islogin", String.valueOf(oInfo.getIsLogin()));

		}
		return oMap;
	}

	/**
	 * 传入节点。解析成Userinfo对象
	 * 
	 * @param oNode
	 * @return
	 */
	public UserInfo getUserInfoByXml(Node oNode) {
		UserInfo oInfo = null;
		if(oNode!=null&&oNode.getNodeType()==Document.ELEMENT_NODE){
			NodeList userNodeList = oNode.getChildNodes();
			if (userNodeList!=null&&userNodeList.getLength()>0) {
				oInfo = new UserInfo();
				for (int i = 0; i < userNodeList.getLength(); i++) {
					Node userNode = userNodeList.item(i);
					if (userNode.getNodeName().equals("id")) {
						oInfo.setId(UtiyCommon.getParseInt(userNode.getFirstChild().getNodeValue()));
					}
					if (userNode.getNodeName().equals("loginname")) {
						oInfo.setLoginName(userNode.getFirstChild().getNodeValue());
					}
					if (userNode.getNodeName().equals("password")) {
						oInfo.setPassWord(userNode.getFirstChild().getNodeValue());
					}
					if (userNode.getNodeName().equals("lasttime")) {
						oInfo.setLastTime(UtiyCommon.stringParseDate(
								userNode.getFirstChild()
								.getNodeValue()));
					}
					if (userNode.getNodeName().equals("registertime")) {
						oInfo.setRegisterTime(UtiyCommon
								.stringParseDate(userNode.getFirstChild()
								.getNodeValue()));
					}
					if (userNode.getNodeName().equals("sex")) {
						oInfo.setSex(userNode.getFirstChild().getNodeValue());
					}
					if (userNode.getNodeName().equals("nikename")) {
						oInfo.setNikeName(userNode.getFirstChild().getNodeValue());
					}
				}
			}
			
		}
		return oInfo;
	}

	/**
	 * 从数据中获取当前登录用户信息
	 */
	public UserInfo getLoginUserInfo() {
		String sql = "select * from userinfo where islogin = 1";
		return readDB(SysConfig.dbHelper.getModel(sql));
	}

	/**
	 * 将数据库返回的值封装成userinfo对象
	 * 
	 * @param oMap
	 * @return
	 */

	public UserInfo readDB(Map<String, String> oMap) {
		UserInfo oInfo = null;
		if (oMap != null && oMap.size() > 0) {
			oInfo = new UserInfo();
			oInfo.setId(UtiyCommon.getParseInt(oMap.get("id")));
			oInfo.setIsLogin(UtiyCommon.getParseInt(oMap.get("islogin")));
			oInfo.setLastTime(UtiyCommon.stringParseDate(oMap.get("lasttime")));
			oInfo.setLoginName(oMap.get("loginname"));
			oInfo.setNikeName(oMap.get("nikename"));
			oInfo.setPassWord(oMap.get("password"));
			oInfo.setRegisterTime(UtiyCommon.stringParseDate(oMap
					.get("registertime")));
			oInfo.setSex(oMap.get("sex"));
		}
		return oInfo;
	}

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
			int pagesize) {
		// 定义返回集
		List<UserInfo> dataList = null;
		// 定义请求地址及参数

		String getUserUrl = SysConfig.serverUrl + url + "?action=3";
		getUserUrl += "&select=" + selectString
				+ "&page="
				+ page
				+ "&pagesize=" + pagesize;
		// 请求服务端并把返回的string加载成XML文档
		Document oDocument = DomXml.loadXml(HttpCommon.doGet(getUserUrl));
		if (oDocument != null) {
			// 获取服务端返回根节点
			Element oElement = oDocument.getDocumentElement();
			// 如果根节点是有效节点
			if (oElement != null
					&& oElement.getNodeType() == Document.ELEMENT_NODE) {
				// 获取返回的xml用户总数
				int userCount = UtiyCommon.getParseInt(oElement
						.getAttribute("count"));
				// 判断是否有返回数据
				if (userCount == 0) {
					AppApplication.toaskMessage("数据已经全部加载");
				} else {
					// 加载子节点
					NodeList userList = oElement.getChildNodes();
					// 如果有Userinfo这个子节点则加载用户信息
					if (userList != null && userList.getLength() > 0) {
						// 初始化返回集合
						dataList = new ArrayList<UserInfo>();
						// 循环将userinfo的xml解析。并添加到集合返回
						for (int i = 0; i < userList.getLength(); i++) {
							UserInfo oInfo = getUserInfoByXml(userList.item(i));
							if (oInfo != null) {
								dataList.add(oInfo);
							}
						}
					}
				}
			}
		}
		// TODO 自动生成的方法存根
		return dataList;
	}

	public String updatepsd(UserInfo oInfo) {
		String url="servlet/UserInfoServlet";
		int errcount=0;
		String errmsg="";
		String errAlert="更改失败";
		String loginUrl = SysConfig.serverUrl+url+"?action=4";
		loginUrl+="&username="+oInfo.getLoginName()+"&nikename="+oInfo.getNikeName()+"&userpsd="+oInfo.getPassWord();
		String resultString = HttpCommon.doGet(loginUrl);
		Document oDocument = DomXml.loadXml(resultString);
		if (oDocument!=null) {
			Element oElement=oDocument.getDocumentElement();
			if (oElement!=null&&oElement.getNodeType()==Document.ELEMENT_NODE) {
				errcount = UtiyCommon.getParseInt(oElement.getAttribute("errcount"),-1);
				errmsg = oElement.getAttribute("errmsg");
				if (errcount==0) {
					NodeList oList = oElement.getChildNodes();
					if(oList!=null&&oList.getLength()>0){
						for (int i = 0; i < oList.getLength(); i++) {
							Node oNode=oList.item(i);
								if (oNode.getNodeName().equals("info")) {
									errAlert=oNode.getFirstChild().getNodeValue();
								}
							}
							
						}
					}
				}else {
					if (!TextUtils.isEmpty(errmsg)) {
						errAlert = errmsg;
					}
					
				}
			}
		return errAlert;
		}
}
