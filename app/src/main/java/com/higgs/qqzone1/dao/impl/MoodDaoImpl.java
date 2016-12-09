package com.higgs.qqzone1.dao.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.text.TextUtils;

import com.higgs.qqzone1.AppApplication;
import com.higgs.qqzone1.common.DomXml;
import com.higgs.qqzone1.common.HttpCommon;
import com.higgs.qqzone1.common.SysConfig;
import com.higgs.qqzone1.common.UtiyCommon;
import com.higgs.qqzone1.dao.MoodDao;
import com.higgs.qqzone1.model.Mood;

public class MoodDaoImpl implements MoodDao {
	String url = SysConfig.serverUrl + "MoodServlet?action=";
	public boolean addMood(Mood oMood) {
		boolean result = false;
		int errcount;
		String errmsg = "";
		String alertMsg = "发布失败";
		String actionString = url + "1&uid=" + oMood.getUserId() + "&fid="
				+ oMood.getFid() + "&mc="
				+ UtiyCommon.urlEncode(oMood.getMoodContext());
		Document oDocument = DomXml.loadXml(HttpCommon.doGet(actionString));
		if (oDocument != null) {
			Element oElement = oDocument.getDocumentElement();
			if (oElement != null
					&& oElement.getNodeType() == Document.ELEMENT_NODE) {
				errcount = UtiyCommon.getParseInt(
						oElement.getAttribute("errcount"), -1);
				errmsg = oElement.getAttribute("errmsg");
				if (errcount != 0) {
					if (!TextUtils.isEmpty(errmsg)) {
						alertMsg = errmsg;
					}
				} else {
					alertMsg = "";
					result = true;
				}
			}
		}
		if (!TextUtils.isEmpty(alertMsg)) {
			AppApplication.toaskMessage(alertMsg);
		}
		// TODO 自动生成的方法存根
		return result;
	}

}
