package com.dylan.shiro.interfaces.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {
	public static String GetRequest(String httpurl) {
		StringBuffer document = new StringBuffer();
		try {
			URL url = new URL(httpurl);// 远程url
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null)
				document.append(line + " ");
			reader.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document.toString();
	}
	
	public static String PostRequest(String url, String param) {
		String content = "";
		HttpURLConnection connection = null;
		InputStream is = null;
		String curLine = null;
		PrintWriter out = null;
		try {
			URL server = new URL(url);
			connection = (HttpURLConnection) server.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new PrintWriter(connection.getOutputStream());
			// 发送请求参数
			// "searchword=%C9%EA%C7%EB%BA%C5%3D91231422%25+and+%B7%A8%C2%C9%D7%B4%CC%AC%B9%AB%B8%E6%C8%D5%3D%281996.01.03%29+"
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			while ((curLine = reader.readLine()) != null) {
				content = content + curLine.replace("&nbsp;", "").trim();
			}
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}

			if (out != null) {
				out.close();

			}

			if (connection != null) {
				connection.disconnect();
			}

		}

		return content;
	}
}
