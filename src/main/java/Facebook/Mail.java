package Facebook;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

    public void sendEmail(String strRecepientAddress,String strMessage)
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

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(strRecepientAddress));
            message.setSubject("You Posted On Facebook Wall");
            StringBuilder str = new StringBuilder();
            str.append("Hi,") ;
            str.append("\n\n") ;
            str.append("You posted the following message on your facebook wall using Facebook Moments App:");
            str.append("\n\n");
            str.append(strMessage);
            str.append("\n\n") ;
            str.append("Thanks,") ;
            str.append("\n") ;
            str.append("Facebook Moments") ;
            message.setText(str.toString());
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}