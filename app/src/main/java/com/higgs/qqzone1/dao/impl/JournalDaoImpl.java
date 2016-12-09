package com.higgs.qqzone1.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.higgs.qqzone1.common.DomXml;
import com.higgs.qqzone1.common.HttpCommon;
import com.higgs.qqzone1.common.SysConfig;
import com.higgs.qqzone1.common.UtiyCommon;
import com.higgs.qqzone1.dao.JournalDao;
import com.higgs.qqzone1.model.Journal;

public class JournalDaoImpl implements JournalDao {

	
	public List<Journal> getList() {
		List<Journal> oList=null;
		String url=SysConfig.serverUrl+"servlet/JournalServlet?action=1";
		String result=HttpCommon.doGet(url);
		Document oDocument=DomXml.loadXml(result);
		if (oDocument!=null) {
			Element oElement=oDocument.getDocumentElement();
			if (oElement!=null&&oElement.getNodeType()==Document.ELEMENT_NODE) {
				NodeList oNodeList=oElement.getChildNodes();
				if (oNodeList.getLength()>0&&oNodeList!=null) {
					oList=new ArrayList<Journal>();
					for (int i = 0; i < oNodeList.getLength(); i++) {
						Node oNode=oNodeList.item(i);
						if (oNode!=null&&oNode.getNodeType()==Document.ELEMENT_NODE) {
							NodeList oNodeList2=oNode.getChildNodes();
							if (oNodeList2!=null&&oNodeList2.getLength()>0) {
								Journal oJournal=new Journal();
								for (int j = 0; j < oNodeList2.getLength(); j++) {
									Node oNode2=oNodeList2.item(j);
									if (oNode2.getNodeName().equals("id")) {
										oJournal.setId(UtiyCommon.getParseInt(oNode2.getFirstChild().getNodeValue()));
									}
									if (oNode2.getNodeName().equals("title")) {
										oJournal.setTitle(oNode2.getFirstChild().getNodeValue());
									}
									if (oNode2.getNodeName().equals("context")) {
										oJournal.setContext(oNode2.getFirstChild().getNodeValue());
									}
									if (oNode2.getNodeName().equals("readcount")) {
										oJournal.setReadCount(UtiyCommon.getParseInt(oNode2.getFirstChild().getNodeValue()));
									}
									if (oNode2.getNodeName().equals("userid")) {
										oJournal.setUserId(UtiyCommon.getParseInt(oNode2.getFirstChild().getNodeValue()));
									}
									if (oNode2.getNodeName().equals("typeid")) {
										oJournal.setTypeId(UtiyCommon.getParseInt(oNode2.getFirstChild().getNodeValue()));
									}
									if (oNode2.getNodeName().equals("posttime")) {
										oJournal.setPostTime(UtiyCommon.stringParseDate(oNode2.getFirstChild().getNodeValue()));
									}
									oList.add(oJournal);
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
