package com.test.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;


public class MailReceives
{
	public static void main(String [] args)throws Exception{
		String pop3server="pop3.163.com";
		String protocol="pop3";
		String username="lqy3411@163.com";
		String password="liangqingyu1229";
		
		Properties props=new Properties();
		props.setProperty("mail.store.protocol",protocol);
		props.setProperty("mail.pop3.host", pop3server);
		Session mailSession=Session.getDefaultInstance(props, null);
		mailSession.setDebug(false);
		Store store=mailSession.getStore(protocol);
		store.connect(pop3server,username, password);
		Folder folder=store.getFolder("inbox");
		folder.open(Folder.READ_WRITE);
		Message []messages=folder.getMessages();
		for(int i=0;i<messages.length;i++){
			String subject=messages[i].getSubject();
			String from=String.valueOf(messages[i].getFrom()[0]);
			System.out.println("第"+(i+1)+"封邮件的主题为："+subject+"\t发信人地址："+from);
			//System.out.println("你想阅读此邮件吗(Yes/No)?");
			//messages[i].writeTo(System.out);
			
			if(messages[i].isMimeType("multipart/*")){
				Multipart mp=(Multipart)messages[i].getContent();
				int bodynum=mp.getCount();
				for(int j=0;j<bodynum;j++){
					BodyPart bodyPart=mp.getBodyPart(i);
					//System.out.println("Contenttype:"+mp.getBodyPart(j).getContentType());
					
					if(mp.getBodyPart(j).isMimeType("text/html")){
						String content=(String)mp.getBodyPart(j).getContent();
						System.out.println("邮件内容："+content);
						System.out.println("************************************");
					} else if(bodyPart.isMimeType("text/plain")) {   
		                System.out.println("plain................."+bodyPart.getContent());   
		            } else if(bodyPart.isMimeType("multipart/*")) {   
		                Multipart mpart = (Multipart)bodyPart.getContent();   
		                parseMultipart(mpart);   
		                   
		            } else if (bodyPart.isMimeType("application/octet-stream")) {   
		                String disposition = bodyPart.getDisposition();   
		                System.out.println(disposition);   
		                if (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT)) {   
		                    String fileName = bodyPart.getFileName();   
		                    InputStream is = bodyPart.getInputStream();   
		                    copy(is, new FileOutputStream("F:\\TestJavaEmail"+fileName));   
		                }   
		            }
				}
				
			}else{
				System.out.println("不支持的邮件类型");
			}
			
		}
		folder.close(false);
		store.close();
	}

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
	
	
	 /**   
     * 文件拷贝，在用户进行附件下载的时候，可以把附件的InputStream传给用户进行下载   
     * @param is   
     * @param os   
     * @throws IOException   
     */   
    public static void copy(InputStream is, OutputStream os) throws IOException {   
        byte[] bytes = new byte[1024];   
        int len = 0;   
        while ((len=is.read(bytes)) != -1 ) {   
            os.write(bytes, 0, len);   
        }   
        if (os != null)   
            os.close();   
        if (is != null)   
            is.close();   
    }   
       
    
    /** 
     * 对复杂邮件的解析 
     * @param multipart 
     * @throws MessagingException 
     * @throws IOException 
     */  
    public static void parseMultipart(Multipart multipart) throws MessagingException, IOException {  
        int count = multipart.getCount();  
        System.out.println("couont =  "+count);  
        for (int idx=0;idx<count;idx++) {  
            BodyPart bodyPart = multipart.getBodyPart(idx);  
            System.out.println(bodyPart.getContentType());  
            if (bodyPart.isMimeType("text/plain")) {  
                System.out.println("plain................."+bodyPart.getContent());  
            } else if(bodyPart.isMimeType("text/html")) {  
                System.out.println("html..................."+bodyPart.getContent());  
            } else if(bodyPart.isMimeType("multipart/*")) {  
                Multipart mpart = (Multipart)bodyPart.getContent();  
                parseMultipart(mpart);  
                  
            } else if (bodyPart.isMimeType("application/octet-stream")) {  
                String disposition = bodyPart.getDisposition();  
                System.out.println(disposition);  
                if (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT)) {  
                    String fileName = bodyPart.getFileName();  
                    InputStream is = bodyPart.getInputStream();  
                    copy(is, new FileOutputStream("D:\\"+fileName));  
                }  
            }  
        }  
    } 
}
