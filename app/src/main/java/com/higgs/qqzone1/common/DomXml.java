package com.higgs.qqzone1.common;

import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class DomXml {
	/**
	 * 根据传入的xml字符串解析成document
	 * @param context
	 * @return
	 */
	public static Document loadXml(String context) {
		DocumentBuilderFactory oFactory = DocumentBuilderFactory.newInstance();
		Document oDocument = null;
		DocumentBuilder oBuilder = null;
		try {
			//通过documentBuilderFactory创建documentBuilder对象
			oBuilder = oFactory.newDocumentBuilder();
			//创建输入源对象
			InputSource is = new InputSource();
			//设置转换流文件
			is.setCharacterStream(new StringReader(context));
			//解析成document对象
			oDocument = oBuilder.parse(is);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return oDocument;
	}
}	
