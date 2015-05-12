package Facebook;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

    public void sendEmail()
    {
        final String username = "project273@yahoo.com";
        final String password = "sjsuspring15";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.mail.yahoo.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("project273@yahoo.com"));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress("jihirsha@yahoo.com"));
            message.setSubject("Post Scheduled");
            message.setText("Email Checking);
            Transport.send(message);

            System.out.println("Email sent..");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}