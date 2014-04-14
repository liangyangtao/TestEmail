package com.test.utils;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

public class Test2
{
	
	private static Header[] headers = {
		new BasicHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; "
				+ "Windows NT 5.1; SV1; .NET CLR 2.0.50727; CIBA)"),
		new BasicHeader("Accept-Language", "zh-cn"),
		new BasicHeader(
				"Accept",
				" image/gif, image/x-xbitmap, image/jpeg, "
						+ "image/pjpeg, application/x-silverlight, application/vnd.ms-excel, "
						+ "application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*"),
		new BasicHeader("Content-Type", "application/x-www-form-urlencoded"),
		new BasicHeader("Accept-Encoding", "gzip, deflate") };
	
	
	
	public boolean email_login(String name,String pwd){
		boolean bl=false;
		
		String wangyi="";
		BasicCookieStore cookieStore = new BasicCookieStore();
		 CloseableHttpClient httpclient = HttpClients.custom()
	                .setDefaultCookieStore(cookieStore)
	                .build();
		HttpPost httpPost = new HttpPost(wangyi);
		
		
		
		
		
		return bl;
	}
}
