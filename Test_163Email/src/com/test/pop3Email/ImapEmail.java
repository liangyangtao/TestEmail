package com.test.pop3Email;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

/**
 * 使用imap协议获取邮件
 * 
 * @author 梁擎宇
 * 
 */
public class ImapEmail {

	public static void main(String[] args) throws Exception {
		String user = "lqy3411@163.com";// 邮箱的用户名
		String password = "liangqingyu1229"; // 邮箱的密码

		Properties prop = System.getProperties();
		prop.put("mail.store.protocol", "imap");
		prop.put("mail.imap.host", "imap.163.com");

		Session session = Session.getInstance(prop);
		
		int total = 0;
		IMAPStore store = (IMAPStore) session.getStore("imap"); // 使用imap会话机制，连接服务器
		store.connect(user, password);
		IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX"); // 收件箱
		folder.open(Folder.READ_WRITE);
		// 获取总邮件数
		total = folder.getMessageCount();
		System.out.println("-----------------共有邮件：" + total
				+ " 封--------------");
		// 得到收件箱文件夹信息，获取邮件列表
		System.out.println("未读邮件数：" + folder.getUnreadMessageCount());
		Message[] messages = folder.getMessages();
		int messageNumber = 0;
		for (Message message : messages) {
			System.out.println("发送时间：" + message.getSentDate());
			System.out.println("主题：" + message.getSubject());
			System.out.println("内容：" + message.getContent());
			
			Flags flags = message.getFlags();
			if (flags.contains(Flags.Flag.SEEN)){
				System.out.println("这是一封已读邮件");
			}
			else {
				System.out.println("这是一封未读邮件");
				System.out.println("发送时间：" + message.getSentDate());
				System.out.println("主题：" + message.getSubject());
				System.out.println("内容：" + message.getContent());
				
				//解析邮件内容
				Object content = message.getContent();
				if (content instanceof MimeMultipart) {
					MimeMultipart multipart = (MimeMultipart) content;
					parseMultipart(multipart);
					message.setFlag(Flags.Flag.SEEN, true);
				}
				
			}
			System.out
					.println("========================================================");
			System.out
					.println("========================================================");
			//每封邮件都有一个MessageNumber，可以通过邮件的MessageNumber在收件箱里面取得该邮件
			//messageNumber = message.getMessageNumber();
		}
		// 释放资源
		if (folder != null)
			folder.close(true); 
		if (store != null)
			store.close();
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
					copy(is, new FileOutputStream("F:\\TestJavaEmail\\"+fileName));
				}
			}
		}
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
}

