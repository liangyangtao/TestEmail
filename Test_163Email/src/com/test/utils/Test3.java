package com.test.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Test3
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

	static final String LOGON_SITE = "https://ssl.mail.163.com/entry/coremail/fcg/ntesdoor2?df=mail163_letter&from=web&funcid=loginone&iframe=1&language=-1&passtype=1&product=mail163&net=c&style=-1&race=-2_62_-2_hz&uid=lqy3411@163.com";
	static final String LOGON_SITE2 = "http://cwebmail.mail.163.com/js5/main.jsp?sid=zCsDFCEEFYkqJHgJTIEENHNCiJElNMPv";
	
	static final int LOGON_PORT = 80;

	public static void main(String[] args)
	{
		//CloseableHttpClient httpclient = HttpClients.createDefault();
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		HttpPost httpPost = new HttpPost(LOGON_SITE);
		BasicCookieStore cookieStore = new BasicCookieStore();
		
		 CloseableHttpClient httpclient = HttpClients.custom()
	                .setDefaultCookieStore(cookieStore)
	                .build();
		
		NameValuePair nameValuePair1 = new BasicNameValuePair("username",
				"lqy3411");
		NameValuePair nameValuePair2 = new BasicNameValuePair("password",
				"liangqingyu1229");

		nvps.add(nameValuePair1);
		nvps.add(nameValuePair2);

		httpPost.setHeaders(headers);

		try
		{
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			HttpResponse response = httpclient.execute(httpPost);

			HttpEntity entity = response.getEntity();
			System.out.println("Login form get: " + response.getStatusLine());
			EntityUtils.consume(entity);

            System.out.println("Initial set of cookies:");
            List<Cookie> cookies = cookieStore.getCookies();
            if (cookies.isEmpty()) {
                System.out.println("None");
            } else {
                for (int i = 0; i < cookies.size(); i++) {
                    System.out.println("- " + cookies.get(i).toString());
                }
            }
			
			/*String s = changeInPutStream(entity.getContent(), "utf-8");

			System.out.println("s-" + s);*/

		} catch (ClientProtocolException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String changeInPutStream(InputStream inputStream,
			String encode)
	{
		ByteArrayOutputStream arrayBuffer = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		String reslut = null;
		int len = 0;
		if (inputStream != null)
		{
			try
			{
				while ((len = inputStream.read(data)) != -1)
				{
					arrayBuffer.write(data, 0, len);
				}
				reslut = new String(arrayBuffer.toByteArray(), encode);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return reslut;
	}
}
