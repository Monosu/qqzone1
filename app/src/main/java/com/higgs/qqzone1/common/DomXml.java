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
			oBuilder = oFactory.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(context));
			oDocument = oBuilder.parse(is);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return oDocument;
	}
}	
