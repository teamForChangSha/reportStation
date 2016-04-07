package com.jxxp.comms.util;

import java.io.File;  
import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Properties;  
  
import javax.mail.Session;  
import javax.mail.internet.MimeMessage;  
  
import org.springframework.core.io.FileSystemResource;  
import org.springframework.mail.javamail.JavaMailSenderImpl;  
import org.springframework.mail.javamail.MimeMessageHelper; 

import com.jxxp.pojo.ReportCase;

public class MailUtil {

    /** 
     * 发件人邮箱服务器 
     */  
    private String emailHost;  
    /** 
     * 发件人邮箱 
     */  
    private String emailFrom;  
  
    /** 
     * 发件人用户名 
     */  
    private String emailUserName;  
  
    /** 
     * 发件人密码 
     */  
    private String emailPassword;  
  
    /** 
     * 收件人邮箱，多个邮箱以“;”分隔 
     */  
    private String toEmails;  
    /** 
     * 邮件主题 
     */  
    private String subject;  
    /** 
     * 邮件内容 
     */  
    private String content;  
    /** 
     * 邮件中的附件，为空时无附件。map中的key为附件ID，value为附件地址 
     */  
    private Map<String, String> attachments;  
 
    public String getEmailHost() {
		return emailHost;
	}

	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailUserName() {
		return emailUserName;
	}

	public void setEmailUserName(String emailUserName) {
		this.emailUserName = emailUserName;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getToEmails() {
		return toEmails;
	}
	
	public void setToEmails(String toEmails) {
		this.toEmails = toEmails;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Map<String, String> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, String> attachments) {
		this.attachments = attachments;
	}

	/** 
     * 发送邮件 
     *  
     * @author cj 
     * @throws Exception 
     */  
    public void sendEmail() throws Exception {  
        if (this.getEmailHost().equals("") || this.getEmailFrom().equals("")  
                || this.getEmailUserName().equals("")  
                || this.getEmailPassword().equals("")) {  
            throw new RuntimeException("发件人信息不完全，请确认发件人信息！");  
        }  
  
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
  
        // 设定mail server  
        senderImpl.setHost(emailHost);  
  
        // 建立邮件消息  
        MimeMessage mailMessage = senderImpl.createMimeMessage();  
  
        MimeMessageHelper messageHelper = null;  
        messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");  
        // 设置发件人邮箱  
        messageHelper.setFrom(emailFrom);  
  
        // 设置收件人邮箱  
        String[] toEmailArray = toEmails.split(";");  
        List<String> toEmailList = new ArrayList<String>();  
        if (null == toEmailArray || toEmailArray.length <= 0) {  
            throw new RuntimeException("收件人邮箱不得为空！");  
        } else {  
            for (String s : toEmailArray) {  
                if (!s.equals("")) {  
                    toEmailList.add(s);  
                }  
            }  
            if (null == toEmailList || toEmailList.size() <= 0) {  
                throw new RuntimeException("收件人邮箱不得为空！");  
            } else {  
                toEmailArray = new String[toEmailList.size()];  
                for (int i = 0; i < toEmailList.size(); i++) {  
                    toEmailArray[i] = toEmailList.get(i);  
                }  
            }  
        }  
        messageHelper.setTo(toEmailArray);  
  
        // 邮件主题  
        messageHelper.setSubject(subject);  
  
        // true 表示启动HTML格式的邮件  
        messageHelper.setText(content, true);  
        
        // 添加附件  
        if (null != attachments) {  
            for (Iterator<Map.Entry<String, String>> it = attachments.entrySet().iterator(); it.hasNext();) {  
                Map.Entry<String, String> entry = it.next();  
                String cid = entry.getKey();  
                String filePath = entry.getValue();  
                if (null == cid || null == filePath) {  
                    throw new RuntimeException("请确认每个附件的ID和地址是否齐全！");  
                }  
  
                File file = new File(filePath);  
                if (!file.exists()) {  
                    throw new RuntimeException("附件" + filePath + "不存在！");  
                }  
  
                FileSystemResource fileResource = new FileSystemResource(file);  
                messageHelper.addAttachment(cid, fileResource);  
            }  
        }  
  
        Properties prop = new Properties();  
        prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确  
        prop.put("mail.smtp.timeout", "25000");  
        // 添加验证  
        MyAuthenticator auth = new MyAuthenticator(emailUserName, emailPassword);  
  
        Session session = Session.getDefaultInstance(prop, auth);  
        senderImpl.setSession(session);  
  
        // 发送邮件  
        senderImpl.send(mailMessage);  
    }  
    
    public void createReportMail(ReportCase reportCase,String emails) {
    	this.setSubject("关于（" + reportCase.getRtList() + ")的举报");
    	this.setToEmails(emails);
    	String content = "";
    	this.setContent(content);
    }
  
}
