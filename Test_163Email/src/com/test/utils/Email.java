package com.test.utils;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import org.apache.http.util.EntityUtils;


public class Email
{
 
  //使用缺省的httpclient
 private DefaultHttpClient client=null;

 private HttpResponse response=null;
 private String html=null;
 
 public boolean openEmail(String user,String password)throws Exception
 {

  client=new DefaultHttpClient();
  client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
  client.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);
  HttpPost httpost = new HttpPost("https://reg.163.com/logins.jsp?url=http://entry.mail.163.com/coremail/fcg/ntesdoor2?lightweight=1&");
  
  Vector<NameValuePair> nvps = new Vector<NameValuePair>();
  
  nvps.add(new BasicNameValuePair("product", "163"));
  nvps.add(new BasicNameValuePair("type", "1"));
  nvps.add(new BasicNameValuePair("ursname",null));
  nvps.add(new BasicNameValuePair("selected","1"));
  nvps.add(new BasicNameValuePair("username", user));
  nvps.add(new BasicNameValuePair("password",password));
  nvps.add(new BasicNameValuePair("submit","登录"));
    
  httpost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
  response = client.execute(httpost);
 
  System.out.println(getRedirect(response,client));
  return true;
 }
 private String getRedirect(HttpResponse response,DefaultHttpClient client) throws Exception
 {
  
  HttpEntity he=response.getEntity();
  html=EntityUtils.toString(he);
  Pattern p=Pattern.compile("window.location.replace\\(\"([^<>\"]+)\"\\)");
  Matcher m=p.matcher(html);
  Pattern p2=Pattern.compile("<a href=\"([^<>]+)\">");
  Matcher m2=p2.matcher(html);
  String reurl=null;
  HttpGet get=null;
  if(m.find())
   {
  reurl=m.group(1);
  get=new HttpGet(reurl);
  response=client.execute(get);
  getRedirect(response,client);
   }
   if(m2.find())
   {
   
  reurl=m2.group(1);
  System.out.println(reurl);
  get=new HttpGet(reurl);
  response=client.execute(get);
  getRedirect(response,client);
   }
   return html;
   
 }

 public static void main(String args[]) throws Exception
 {
  Email e=new Email();
  e.openEmail("lqy3411", "liangqingyu1229");
 }
}

