package com.higgs.qqzone1.dao.impl;

import java.io.File;
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
import com.higgs.qqzone1.dao.UserFilesDao;
import com.higgs.qqzone1.model.UserFiles;

public class UserFilesDaoImpl implements UserFilesDao {
	String url = SysConfig.serverUrl + "UserFilesServlet?";

	public UserFiles uploadFiles(UserFiles oFiles) {
		// TODO 自动生成的方法存根
		UserFiles userFiles = null;
		String actionUrl = url + "&action=1";
		int errCount = 0;
		String errMsg = "";
		String alertMsg = "上传失败";
		File oFile = new File(oFiles.getFilePath());
		if (oFile.exists()) {
			String result = HttpCommon.uploadFileToServer(actionUrl, oFile);
			Document oDocument = DomXml.loadXml(result);
			if (oDocument != null) {
				Element oElement = oDocument.getDocumentElement();
				if (oElement != null
						&& oElement.getNodeType() == Document.ELEMENT_NODE) {
					errCount = UtiyCommon.getParseInt(
							oElement.getAttribute("errcount"), -1);
					errMsg = oElement.getAttribute("errmsg");
					if (errCount != 0) {
						if (!TextUtils.isEmpty(errMsg)) {
							alertMsg = errMsg;
						}
					} else {
						NodeList oList = oElement.getChildNodes();
						if (oList != null && oList.getLength() > 0) {
							userFiles = getUserFilesByXml(oList.item(0));
							if (userFiles != null) {
								alertMsg = "";
							}
						}
					}
				}
			}
		}
		if (!TextUtils.isEmpty(alertMsg)) {
			AppApplication.toaskMessage(alertMsg);
		}
		return userFiles;
	}

	public UserFiles getUserFilesByXml(Node oNode) {
		UserFiles oFiles = null;
		if (oNode != null && oNode.getNodeType() == Document.ELEMENT_NODE) {
			NodeList fileList = oNode.getChildNodes();
			if (fileList != null && fileList.getLength() > 0) {
				oFiles = new UserFiles();
				for (int i = 0; i < fileList.getLength(); i++) {
					Node userFileNode = fileList.item(i);
					if (userFileNode.getNodeName().equals("id")) {
						oFiles.setId(UtiyCommon.getParseInt(userFileNode
								.getFirstChild().getNodeValue()));
					} else if (userFileNode.getNodeName().equals("fileexp")) {
						oFiles.setFileExp(userFileNode.getFirstChild()
								.getNodeValue());
					} else if (userFileNode.getNodeName().equals("filename")) {
						oFiles.setFileName(userFileNode.getFirstChild()
								.getNodeValue());
					} else if (userFileNode.getNodeName().equals("filepath")) {
						oFiles.setFilePath(userFileNode.getFirstChild()
								.getNodeValue());
					} else if (userFileNode.getNodeName().equals("filesize")) {
						oFiles.setFileSize(UtiyCommon.getParseInt(userFileNode
								.getFirstChild().getNodeValue()));
					} else if (userFileNode.getNodeName().equals("uploadtime")) {
						oFiles.setUploadTime(UtiyCommon
								.stringParseDate(userFileNode.getFirstChild()
										.getNodeValue()));
					}

				}
				if (oFiles.getId() <= 0) {
					oFiles = null;
				}
			}
		}
		return oFiles;
	}

	@Override
	public List<UserFiles> getFilesPath() {
		List<UserFiles> oList=null;
		String actionUrl = url + "&action=2";
		String result=HttpCommon.doGet(actionUrl);
		Document oDocument=DomXml.loadXml(result);
		if (oDocument!=null) {
			Element oElement=oDocument.getDocumentElement();
			if (oElement!=null&&oElement.getNodeType()==Document.ELEMENT_NODE) {
				NodeList oNodeList=oElement.getChildNodes();
				if (oNodeList!=null&&oNodeList.getLength()>0) {
					oList=new ArrayList<UserFiles>();
					for (int i = 0; i < oNodeList.getLength(); i++) {
						Node oNode=oNodeList.item(i);
						if (oNode!=null&&oNode.getNodeType()==Document.ELEMENT_NODE) {
							NodeList oList2=oNode.getChildNodes();
							if (oList2!=null&&oList2.getLength()>0) {
								UserFiles oFiles=new UserFiles();
								for (int j = 0; j < oList2.getLength(); j++) {
									Node oNode2=oList2.item(j);
									if (oNode2.getNodeName().equals("filepath")) {
										oFiles.setFilePath(oNode2.getFirstChild().getNodeValue());
									}
									oList.add(oFiles);
								}
							}
						}
					}
				}
			}
		}
		return oList;
	}
}
