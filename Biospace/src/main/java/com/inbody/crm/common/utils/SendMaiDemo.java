package com.inbody.crm.common.utils;
import java.util.Properties;  
import javax.mail.Address;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  
public class SendMaiDemo {  
    public static void main(String[] args) throws Exception {  
        Properties props = new Properties();  
        props.put("mail.smtp.host", "smtp.partner.outlook.cn");  
        props.put("mail.smtp.auth", "true"); 
        // 加密方法: TLS ，TLS不是SSL  所以我在我的配置上加上了配置   proper.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.enable", "true");
        //基本的邮件会话，Session对象利用了java.util.Properties对象获得了邮件服务器、用户名、密码信息和整个应用程序都要使用到的共享信息。  
        Session session = Session.getInstance(props);  
        //建立了Session对象后，便可以被发送的构造信息体Message抽象类  
        MimeMessage message = new MimeMessage(session);  
        //发件地址  
        Address address = new InternetAddress("test@inbodychina.com");  
        //设置发信人  
        message.setFrom(address);  
        //收件地址  
        Address toAddress = new InternetAddress("1142378531@qq.com");  
        //设置收信人,Message.RecipientType.TO 收信人，Message.RecipientType.CC抄送人  
        message.setRecipient(MimeMessage.RecipientType.TO, toAddress);  
        //主题   
        message.setSubject("hello world");  
        //正文   
        message.setText("hello world");  
        //保存文件   
        message.saveChanges();  
        //监控邮件发送过程  
        session.setDebug(true);   
        //获取实现了SMTP协议的Transport类  
        Transport transport = session.getTransport("smtp");  
        //连接服务器(依次填入邮件服务器、用户名、密码信息)  
        transport.connect("smtp.partner.outlook.cn", "test@inbodychina.com", "InBody0.");  
        //发送  
        transport.sendMessage(message, message.getAllRecipients());  
        //关闭连接  
        transport.close();  
    }  
}