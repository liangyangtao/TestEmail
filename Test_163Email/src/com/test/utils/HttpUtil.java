package com.test.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;


public class HttpUtil
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
	/*public  String postMethod(HttpParameter hp) {
		
		String s="";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(hp.getUrl());
		httpPost.setEntity(new UrlEncodedFormEntity(hp.getNvps(), Consts.UTF_8));
		httpPost.setHeaders(headers);
		try {
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			s = changeInPutStream(entity.getContent(), EntityUtils.getContentCharSet(entity));
			
		} catch (ClientProtocolException e){
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		return s;
	}

	public  String GetMethod(HttpParameter hp){
		String s="";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet=new HttpGet(hp.getUrl());
		try {
			HttpResponse response=	httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			s = changeInPutStream(entity.getContent(), EntityUtils.getContentCharSet(entity));
			
		} catch (ClientProtocolException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		return s;
	}*/
	
	private static String changeInPutStream(InputStream inputStream,
			String encode) {
		ByteArrayOutputStream arrayBuffer = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		String reslut = null;
		int len = 0;
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					arrayBuffer.write(data, 0, len);
				}
				reslut = new String(arrayBuffer.toByteArray(), encode);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return reslut;
	}
	
}
