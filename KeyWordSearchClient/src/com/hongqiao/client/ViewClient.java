package com.hongqiao.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class ViewClient {
	public static String sendGet(String url, String param){
		String result = "";
		String urlName = url + "?" + param;
		try{
			URL realUrl = new URL(urlName);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
			conn.connect();
			Map<String,List<String>> map = conn.getHeaderFields();
			for (String key : map.keySet()) {
				System.out.println(key + "-->" + map.get(key));
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
		
	}
	
	public static String sendPost(String url, String param){
		String result = "";
		try{
			URL realUrl = new URL(url);
			URLConnection conn =  realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			PrintWriter out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
