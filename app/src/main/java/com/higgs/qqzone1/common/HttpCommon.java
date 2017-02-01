package com.higgs.qqzone1.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import android.util.Log;

import com.higgs.qqzone1.AppApplication;



/**
 * @author 吴杰泉 日期：2013-08-07 描述：服务器http通讯连接类
 */
public class HttpCommon {
	private static final int REQUEST_TIMEOUT = 5 * 1000;// 设置请求超时5秒钟
	private static final int SO_TIMEOUT = 5 * 1000; // 设置等待数据超时时间5秒钟
	private static HttpClient oClient = getHttpClient();
	private static String serviceUrl = "";

	/**
	 * 使用get方式从服务器获取信息
	 * 
	 * @param url
	 *            访问的具体页面
	 * @param parm
	 *            需要传输的参数
	 * @return 服务端返回信息
	 */
	public static String doGet(String url, HashMap<String, String> parm) {
		if (!AppApplication.isWork()) {
			return "";
		}

		StringBuffer getUrl = new StringBuffer(serviceUrl + url);
		// 判断是否需要传递参数
		if (parm != null) {
			// 如果url没有存在?需要先添加?
			//indexOf()返回字符在字符串中首次出现的位置从0开始,
			// 返回字符中indexof（string）中子串string在父串中首次出现的位置，从0开始！没有返回-1；
			if (getUrl.indexOf("?") < 0) {
				getUrl.append("?");
			}
			// 判断URL如果有带参数,并且最后一个不是&则追加一个&
			//lastIndexOf()返回一个字符在字符串中最后出现的位置从0开始
			int lastIndex = getUrl.lastIndexOf("&");
			if (lastIndex > 0 && lastIndex != getUrl.length() - 1) {
				getUrl.append("&");
			}
			// 循环拼接url
			for (String key : parm.keySet()) {
				getUrl.append(key + "=" + parm.get(key));
				getUrl.append("&");
			}
			//删掉最后个&
			getUrl.delete(getUrl.length() - 1, getUrl.length());
		}
		String result = "";
		/**
		 * 1.如果要操作少量的数据用 = String
		 * 2.单线程操作字符串缓冲区 下操作大量数据 = StringBuilder
		 * 3.多线程操作字符串缓冲区 下操作大量数据 = StringBuffer
		 */
		StringBuffer oBuffer = new StringBuffer();
		// 通过url创建httpGet对象
		HttpGet oGet = new HttpGet(getUrl.toString());

		InputStream oInputStream = null;
		try {
			testCharset(getUrl.toString());
			// 通过HttpClient执行请求.获取服务器返回的信息
			HttpResponse oResponse = oClient.execute(oGet);
			// 如果响应码为200则表示通讯正常
			 if (oResponse.getStatusLine().getStatusCode() == 200) {
				// 获取返回的参数并读取返回
				HttpEntity oEntity = oResponse.getEntity();
				oInputStream = oEntity.getContent();
				InputStreamReader oBufferedReader = new InputStreamReader(
						oInputStream, "utf-8");
				BufferedReader oBufferedReader2 = new BufferedReader(
						oBufferedReader);
				oBuffer.append(oBufferedReader2.readLine());
//				 byte[] a_catch = new byte[64];
//				 int hasRead = 0;
//				 // oBufferedReader2.read();
//				 while ((hasRead = oInputStream.read(a_catch)) != -1) {
//					 oBuffer.append(new String(a_catch, 0, hasRead));
//				 }
			 }
		} catch (ClientProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (oInputStream != null) {
				try {
					oInputStream.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		testCharset(oBuffer.toString());
		// AppApplication.toaskMessage(oBuffer.toString());
		return oBuffer.toString();
	}

	/**
	 * 使用get方式从服务器获取信息
	 * 
	 * @param url
	 *            访问的具体页面
	 * @return 服务端返回信息
	 */
	public static String doGet(String url) {
		return doGet(url, null);
	}

	/**
	 * 使用post方式从服务器获取信息
	 * 
	 * @param url
	 *            访问的具体页面
	 * @return 服务端返回信息
	 */
	public static String doPost(String url) {
		return doGet(url, null);
	}

	/**
	 * 使用post方式从服务器获取信息
	 * 
	 * @param url
	 *            访问的具体页面
	 * @param parmt
	 *            需要传输的参数
	 * @return 服务端返回信息
	 */
	public static String doPost(String url, HashMap<String, String> parmt) {
		// if (!AppApplication.isNetworkConnected()) {
		// return "";
		// }
		testCharset(url);
		StringBuffer s_ResultBuffer = new StringBuffer();
		List<NameValuePair> oList = null;
		// 判断是否需要传输参数
		if (parmt != null) {
			oList = new ArrayList<NameValuePair>();
			BasicNameValuePair oBasicNameValuePair = null;
			// Post参数需要通过BasicNameValuePair
			// 实例化NameValuePair参数值。构造函数第一个值为key,第二个值为value。循环添加到List里面
			for (String key : parmt.keySet()) {
				oBasicNameValuePair = new BasicNameValuePair(key,
						parmt.get(key));

				oList.add(oBasicNameValuePair);
			}
		}
		InputStream oInputStream = null;
		try {
			// 通过url.实例化一个HttpPost对象
			HttpPost oPost = new HttpPost(serviceUrl + url);
			// 如果参数列表不为空。则添加传输参数

			if (oList != null) {
				HttpEntity oEntity = new UrlEncodedFormEntity(oList, HTTP.UTF_8);
				oPost.setEntity(oEntity);
			}
			// 连接服务器
			HttpResponse oResponse = oClient.execute(oPost);
			if (oResponse.getStatusLine().getStatusCode() == 200) {
				// 获取返回值.并读取返回
				oInputStream = oResponse.getEntity().getContent();
				byte[] a_catch = new byte[64];
				int hasRead = 0;
				while ((hasRead = oInputStream.read(a_catch)) != -1) {
					s_ResultBuffer.append(new String(a_catch, 0, hasRead));
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (oInputStream != null) {
				try {
					oInputStream.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		testCharset(s_ResultBuffer.toString());
		// AppApplication.toaskMessage(s_ResultBuffer.toString());
		return s_ResultBuffer.toString();
	}

	// 以下是测试字符编码的
	public static void testCharset(String datastr) {
		try {
			String temp = new String(datastr.getBytes(), "GBK");
			Log.v("TestCharset", "****** getBytes() -> GBK ******/n" + temp);
			temp = new String(datastr.getBytes("GBK"), "UTF-8");
			Log.v("TestCharset", "****** GBK -> UTF-8 *******/n" + temp);
			temp = new String(datastr.getBytes("GBK"), "ISO-8859-1");
			Log.v("TestCharset", "****** GBK -> ISO-8859-1 *******/n" + temp);
			temp = new String(datastr.getBytes("ISO-8859-1"), "UTF-8");
			Log.v("TestCharset", "****** ISO-8859-1 -> UTF-8 *******/n" + temp);
			temp = new String(datastr.getBytes("ISO-8859-1"), "GBK");
			Log.v("TestCharset", "****** ISO-8859-1 -> GBK *******/n" + temp);
			temp = new String(datastr.getBytes("UTF-8"), "GBK");
			Log.v("TestCharset", "****** UTF-8 -> GBK *******/n" + temp);
			temp = new String(datastr.getBytes("UTF-8"), "ISO-8859-1");
			Log.v("TestCharset", "****** UTF-8 -> ISO-8859-1 *******/n" + temp);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static HttpClient getHttpClient() {
		// BasicHttpParams httpParams = new BasicHttpParams();
		// httpParams
		// .setParameter(
		// "UserAgent",
		// "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
		// HttpConnectionParams.setConnectionTimeout(httpParams,
		// REQUEST_TIMEOUT);
		// HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		// HttpClient client = new DefaultHttpClient(httpParams);
		HttpClient client = new DefaultHttpClient();
		// oClient = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				SO_TIMEOUT);

		return client;
	}

	public static String uploadFile(String fileName, String requesturl) {
		// byte[] data = xml.getBytes(encoding);
		String result = "";
		File oFile = new File(fileName);
		try {
			URL url = new URL(requesturl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "text/xml; charset="
					+ "utf-8");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setRequestProperty("Content-Length",
					String.valueOf(oFile.length()));
			conn.setConnectTimeout(20 * 1000);
			int test = (int) oFile.length();
			OutputStream outStream = conn.getOutputStream();
			InputStream is = new FileInputStream(oFile);

			byte[] bytes = new byte[1024];
			// is.read(bytes, 0, test);
			// outStream.write(bytes);
			int len = 0;
			int curLen = 0;
			while ((len = is.read(bytes)) != -1) {
				curLen += len;
				outStream.write(bytes, 0, len);
			}

			// outStream.write(data);
			outStream.flush();
			outStream.close();
			int a = conn.getResponseCode();

			 if (conn.getResponseCode() == 200) {
				if (conn.getInputStream() != null) {
					result = new String(readStream(conn.getInputStream()));
				}

			 }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Log.i("", result);
		return result;
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	/* 上传文件至Server的方法 */
	public static String uploadFileToServer(String urlString, File oFile) {
		String result = "";
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(20 * 1000);
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file1\";filename=\"" + oFile.getName() + "\""
					+ end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			FileInputStream fStream = new FileInputStream(oFile);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();
			/* 取得Response内容 */

			if (con.getResponseCode() == 200) {
				result = new String(readStream(con.getInputStream()));
			}
			// InputStream is = con.getInputStream();
			// int ch;
			// StringBuffer b = new StringBuffer();
			// while ((ch = is.read()) != -1) {
			// b.append((char) ch);
			// }
			// /* 将Response显示于Dialog */
			// if (b.toString().equals("1")) {
			// result = true;
			// }
			// showDialog("上传成功" + b.toString().trim());
			/* 关闭DataOutputStream */
			ds.close();
		} catch (Exception e) {
			// showDialog("上传失败" + e);
		}
		return result;
	}
}
