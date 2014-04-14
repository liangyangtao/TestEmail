package com.test.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

//附加简单的获取html页面代码：

public class ClientWithResponseHandler {



   public final static void main(String[] args) throws Exception {

       

       HttpClient httpclient = new DefaultHttpClient();



       HttpGet httpget = new HttpGet("http://www.163.com"); 

       

       HttpResponse response =httpclient.execute(httpget);

       HttpEntity entity=response.getEntity();

       String html = new String(EntityUtils.toString(entity));

      html=new String(html.getBytes("ISO-8859-1"),"GBK");//解决中文乱码

       System.out.println(html);

       httpclient.getConnectionManager().shutdown();        

   }

   

}
