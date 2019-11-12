/** 
 * @author lxm
 * @create_date 2019.5.3
 * @description 邮件发送工具类
 * */
package com.app.tools;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.util.MailSSLSocketFactory;


/**
 * 
 * @author Qixuan.Chen
 */
public class SendEmail {
    
    public static final String HOST = "smtp.exmail.qq.com";
    public static final String PROTOCOL = "smtp";
    public static final int PORT = 465;
    public static final String FROM = "admin@playrust.shop";//�����˵�email
    public static final String PWD = "19850402Zt";//����������
    
    /**
     * ��ȡSession
     * @return
     */
    private static Session getSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);//���÷�������ַ
        props.put("mail.store.protocol" , PROTOCOL);//����Э��
        props.put("mail.smtp.port", PORT);//���ö˿�
        props.put("mail.smtp.auth" , true);
        
        Authenticator authenticator = new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PWD);
            }
            
        };
        Session session = Session.getDefaultInstance(props , authenticator);
        
        return session;
    }
    
    public static void send(String toEmail ,String Subject ,String content) {
        Session session = getSession();
        try {
            System.out.println("--send--"+content);
            // Instantiate a message
            Message msg = new MimeMessage(session);
 
            //Set message attributes
            msg.setFrom(new InternetAddress(FROM));
            InternetAddress[] address = {new InternetAddress(toEmail)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(Subject);
            msg.setSentDate(new Date());
            msg.setContent(content , "text/html;charset=utf-8");
 
            //Send the message
            Transport.send(msg);
        }
        catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    
    @SuppressWarnings("static-access")
    public static void sendMessage(String alias,String to, String subject,  
            String messageText, String messageType) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {  
        // ��һ��������javax.mail.Session����  
        System.out.println("Ϊ" + HOST + "����mail session����");  


        Properties props = new Properties();  
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.starttls.enable", "true");// ʹ�� STARTTLS��ȫ����
        props.put("mail.smtp.auth", "true"); // ʹ����֤
        // props.put("mail.debug", "true");

        //-------����ʹ��SSL��֤ʱ��ӣ����䲻��SSL��֤ʱɾ�����ɣ�����SSL��֤ʹ��QQ��ҵ���䣩
        String SSL_FACTORY="javax.net.ssl.SSLSocketFactory"; 
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.port", "465"); //googleʹ��465��587�˿�
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true); 
        props.put("mail.smtp.ssl.socketFactory", sf);
        //-------
        
        Session mailSession = Session.getInstance(props,new MyAuthenticator(FROM,PWD));  

        // �ڶ�������д��Ϣ  
        System.out.println("��д��Ϣfrom����to:" + FROM + "����" + to);  

        InternetAddress fromAddress = new InternetAddress(FROM,alias);  
        InternetAddress toAddress = new InternetAddress(to);  

        MimeMessage message = new MimeMessage(mailSession);  

        message.setFrom(fromAddress);  
        message.addRecipient(RecipientType.TO, toAddress);  

        message.setSentDate(Calendar.getInstance().getTime());  
        message.setSubject(subject);  
        message.setContent(messageText, messageType);  

        // ��������������Ϣ  
        Transport transport = mailSession.getTransport("smtp");  
        transport.connect(HOST,FROM, PWD);  
        transport.send(message, message.getRecipients(RecipientType.TO));  
        System.out.println("message yes");  
    }
    
    
    @SuppressWarnings("static-access")
    public static void sendMessageWithFile(String alias,String to, String subject,  
            String messageText, String messageType,String filename) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {  
        // ��һ��������javax.mail.Session����  
        System.out.println("Ϊ" + HOST + "����mail session����");  


        Properties props = new Properties();  
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.starttls.enable", "true");// ʹ�� STARTTLS��ȫ����
        props.put("mail.smtp.auth", "true"); // ʹ����֤
        // props.put("mail.debug", "true");

        //-------����ʹ��SSL��֤ʱ��ӣ����䲻��SSL��֤ʱɾ�����ɣ�����SSL��֤ʹ��QQ��ҵ���䣩
        String SSL_FACTORY="javax.net.ssl.SSLSocketFactory"; 
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.port", "465"); //googleʹ��465��587�˿�
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true); 
        props.put("mail.smtp.ssl.socketFactory", sf);
        //-------
        
        Session mailSession = Session.getInstance(props,new MyAuthenticator(FROM,PWD));  

        // �ڶ�������д��Ϣ  
        System.out.println("��д��Ϣfrom����to:" + FROM + "����" + to);  

        InternetAddress fromAddress = new InternetAddress(FROM,alias);  
        InternetAddress toAddress = new InternetAddress(to);  

        MimeMessage message = new MimeMessage(mailSession);  

        message.setFrom(fromAddress);  
        message.addRecipient(RecipientType.TO, toAddress);  

        message.setSentDate(Calendar.getInstance().getTime());  
        message.setSubject(subject);  

     // ������Ϣ����
        BodyPart messageBodyPart = new MimeBodyPart();

        // ��Ϣ
        messageBodyPart.setText(messageText);

        // ����������Ϣ
        Multipart multipart = new MimeMultipart();

        // �����ı���Ϣ����
        multipart.addBodyPart(messageBodyPart);

        // ��������
        messageBodyPart = new MimeBodyPart();
        // ����Ҫ���͸������ļ�·��
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));

        // messageBodyPart.setFileName(filename);
        // �������������ģ������ļ�·������������
        messageBodyPart.setFileName(MimeUtility.encodeText("��ˮ��ϸ.pdf"));
        multipart.addBodyPart(messageBodyPart);
        
        message.setContent(multipart);
        
        // ��������������Ϣ  
        Transport transport = mailSession.getTransport("smtp");  
        transport.connect(HOST,FROM, PWD);  
        transport.send(message, message.getRecipients(RecipientType.TO));  
        System.out.println("message yes");  
    }
    
     
}


class MyAuthenticator extends Authenticator{  
    String userName="";  
    String password="";  
    public MyAuthenticator(){  

    }  
    public MyAuthenticator(String userName,String password){  
        this.userName=userName;  
        this.password=password;  
    }  
     protected PasswordAuthentication getPasswordAuthentication(){     
        return new PasswordAuthentication(userName, password);     
     }   
}