/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;

import java.util.Properties;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Manoj
 */
public class SendEmail extends RecursiveTask {

    MimeMultipart multipart;
    String Subject;
    String To;

    public SendEmail(String Subject, String To, MimeMultipart multipart) {
        this.multipart = multipart;
        this.Subject = Subject;
        this.To = To;
    }

    @Override
    protected Object compute() {
        // Recipient's email ID needs to be mentioned.
        String to = "destinationemail@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "noreply.azmechatech@gmail.com";
        final String username = "";//change accordingly
        final String password = "";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "localhost";

        Properties props = new Properties();
     // props.put("mail.smtp.auth", "true");
        // props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        //props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(To));

            // Set Subject: header field
            message.setSubject(Subject);

         // This mail has 2 part, the BODY and the embedded image
            // MimeMultipart multipart = new MimeMultipart("related");
         // first part (the html)
            //BodyPart messageBodyPart = new MimeBodyPart();
            //String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
            //messageBodyPart.setContent(htmlText, "text/html");
            // add it
            // multipart.addBodyPart(messageBodyPart);
         // second part (the image)
            //messageBodyPart = new MimeBodyPart();
            //DataSource fds = new FileDataSource(
            //   "/home/manisha/javamail-mini-logo.png");
         //messageBodyPart.setDataHandler(new DataHandler(fds));
            //messageBodyPart.setHeader("Content-ID", "<image>");
         // add image to the multipart
            //multipart.addBodyPart(messageBodyPart);
            // put everything together
            message.setContent(multipart);
            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

    public static MimeMultipart getHTMLEmbdEmail(String H1, String imageURL) throws MessagingException {
        //   This mail has 2 part, the BODY and the embedded image
        MimeMultipart multipart = new MimeMultipart("related");

        //   first part (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<H1>" + H1 + "</H1><img src=\"cid:image\">";
        messageBodyPart.setContent(htmlText, "text/html");
        //   add it
        multipart.addBodyPart(messageBodyPart);

        //   second part (the image)
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource(
                imageURL);

        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");
        messageBodyPart.setFileName(imageURL.contains(System.getProperty("file.separator"))?imageURL.substring(imageURL.lastIndexOf(System.getProperty("file.separator"))+1):imageURL);
        messageBodyPart.setDescription("Photo from mobile device.");
        //   add image to the multipart
        multipart.addBodyPart(messageBodyPart);

        return multipart;
    }

    public static void main(String... args) throws MessagingException {
       SendEmail.sendAsyncEmail("Hello", "hemuman@gmail.com", SendEmail.getHTMLEmbdEmail("Hello Manoj!", "MCW_RANK_1_.png"));
       
       
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendAsyncEmail(String Subject, String To, MimeMultipart multipart) {
        Globals.fjPool.submit(new SendEmail(Subject, To, multipart));
    }

}

class Globals {
    static ForkJoinPool fjPool = new ForkJoinPool(8);
}
