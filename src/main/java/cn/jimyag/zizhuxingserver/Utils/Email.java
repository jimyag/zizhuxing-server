package cn.jimyag.zizhuxingserver.Utils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class Email {
    public String emailHost = "smtp.126.com";       //发送邮件的主机
    public String transportType = "smtp";           //邮件发送的协议
    public String fromUser = "jimyag";           //发件人名称
    public String fromEmail = "jimyag@126.com";  //发件人邮箱
    public String authCode = "1111111111";    //发件人邮箱授权码


    public void Send(String toEmail, String subject, String content) throws UnsupportedEncodingException, javax.mail.MessagingException {

        //初始化默认参数
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", transportType);
        props.setProperty("mail.host", emailHost);
        props.setProperty("mail.user", fromUser);
        props.setProperty("mail.from", fromEmail);
        //获取Session对象
        Session session = Session.getInstance(props, null);
        //开启后有调试信息
        session.setDebug(true);

        //通过MimeMessage来创建Message接口的子类
        MimeMessage message = new MimeMessage(session);
        //下面是对邮件的基本设置
        //设置发件人：
        //设置发件人第二种方式：发件人信息拼接显示：资助星 <jimyag@126.com>
        String formName = MimeUtility.encodeWord("资助星") + " <" + fromEmail + ">";
        InternetAddress from = new InternetAddress(formName);
        message.setFrom(from);

        //设置收件人：
        InternetAddress to = new InternetAddress(toEmail);
        message.setRecipient(Message.RecipientType.TO, to);

        //设置邮件主题
        message.setSubject(subject);

        //设置邮件内容,这里我使用html格式，其实也可以使用纯文本；纯文本"text/plain"
        message.setContent(content, "text/html;charset=UTF-8");

        //保存上面设置的邮件内容
        message.saveChanges();

        //获取Transport对象
        Transport transport = session.getTransport();
        //smtp验证，就是你用来发邮件的邮箱用户名密码（若在之前的properties中指定默认值，这里可以不用再次设置）
        transport.connect(null, null, authCode);
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients()); // 发送
    }

}
