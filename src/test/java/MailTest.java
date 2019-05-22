import org.junit.Test;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailTest {
    //服务商提供的邮箱端口
    public static final String SMTPSERVER = "smtp.163.com";
    //服务商提供的邮箱端口号 若25不行的话设置为465
    public static final String SMTPPORT = "25";
    //输入要发送的邮箱
    public static final String ACCOUT = "自己的邮箱@163.com";
    //邮箱授权码
    public static final String PWD = "自己的授权码";
    @Test
    public void testSendEmail() throws Exception{
        //加载邮件相关配置
        Properties props = getMailProperties();

        //根据邮件创建会话，注意session包别倒错
        Session session = Session.getDefaultInstance(props);
        //开启debug模式，可以看到更多详细的输出日志
        session.setDebug(true);
        //创建邮件
        MimeMessage message = createEmail(session);
        //获取传输通道
        Transport transport = session.getTransport();
        //连接传输通道
        transport.connect(SMTPSERVER, ACCOUT, PWD);
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }



    private Properties getMailProperties() {
        /**
         * 设置邮件相关配置
         *
         */
        //1.创建邮件配置
        Properties props = new Properties();

        //2.使用协议
        props.setProperty("mail.transport.protocol","smtp" );

        //3.发件人的邮箱 SMTP 服务器地址
        props.setProperty("mail.smtp.host", SMTPSERVER);
        //SMTP 服务器端口号，默认为25，因为使用SSL连接方式，所以端口号为456
        //为什么要使用SSL，加密传输，安全

        props.setProperty("mail.smtp.port", SMTPPORT);
        props.put("mail.smtp.socletFactory.class", "javax.nex.ssl.SSLSocketFactory");
        props.setProperty("mail.smp.ssl.enable", "true");
        //设置需求请求认证
        props.setProperty("mail.smtp.auth", "true");
        return props;
    }
    /**
     * 创建邮件
     * @param session
     * @return
     */
    private MimeMessage createEmail(Session session) throws Exception {
        //根据会话创建邮件
        MimeMessage msg = new MimeMessage(session);
        //address 邮件地址，personal 邮件昵称，charset 编码格式
        InternetAddress fromAddress = new InternetAddress(ACCOUT, "Dear", "utf-8");
        //设置发送邮件方
        msg.setFrom(fromAddress);
        InternetAddress receiveAddress  = new InternetAddress("自己的邮箱@163.com", "test", "utf-8");
        //设置邮件接收方
        msg.setRecipients(Message.RecipientType.TO, String.valueOf(receiveAddress));
        //设置邮件标题
        msg.setSubject("测试标题","utf-8");
        msg.setText("点个赞");
        //设置发送时间
        msg.setSentDate(new Date());
        //保存設置
        msg.saveChanges();
        return msg;

    }
}
