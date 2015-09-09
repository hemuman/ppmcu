/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mk.FJTasks;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
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
import static server.GenericUploadHandler.logger;

/**
 *
 * @author Manoj
 */
public class SendEmail extends RecursiveTask {

    MimeMultipart multipart;
    String Subject;
    String To[],H1,imageURL;String[] imageURLs;
    boolean multipleAttchment=false;

    public SendEmail(String Subject, String[] To, String H1, String imageURL) {
        //this.multipart = multipart;
        this.Subject = Subject;
        this.H1=H1;
        this.To = To;
        multipleAttchment=false;
        this.imageURL=imageURL;
    }
    
    public SendEmail(String Subject, String[] To, String H1, String[] imageURLs) {
        multipleAttchment=true;
        //this.multipart = multipart;
        this.H1=H1;
        this.Subject = Subject;
        this.To = To;
        this.imageURLs=imageURLs;
    }

    @Override
    protected Object compute() {
        logger.info(To+":"+Subject+":"+H1);  
        // Recipient's email ID needs to be mentioned.
        String to = "destinationemail@gmail.com";

        // Sender's email ID needs to be mentioned
       // String from = "qichik@ec2-52-27-212-154.us-west-2.compute.amazonaws.com";
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
           // message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            for(String to_:To)
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to_));

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
            
            if (multipleAttchment) {
                try {
                    multipart = getHTMLEmbdEmail(H1, imageURLs);
                } catch (MessagingException ex) {
                    Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
            
                try {
                    multipart = getHTMLEmbdEmail(H1, imageURL);
                } catch (MessagingException ex) {
                    Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
             
            message.setContent(multipart);
            logger.info(To[0]+":"+Subject+":"+H1+"Sending..");  
            // Send message
            Transport.send(message);
            logger.info(To[0]+":"+Subject+":"+H1+"Sent."); 
            System.out.println("Sent message successfully....");
            
            //Delete files:
            
            if (multipleAttchment) {
                for (String filePath : imageURLs) {
                    Path path = null;
                    try {
                        path = FileSystems.getDefault().getPath("", filePath);
                        Files.delete(path);
                    } catch (NoSuchFileException x) {
                        System.err.format("%s: no such" + " file or directory%n", path);
                    } catch (DirectoryNotEmptyException x) {
                        System.err.format("%s not empty%n", path);
                    } catch (IOException x) {
                        // File permission problems are caught here.
                        System.err.println(x);
                    }
                }
            } else {
                Path path = null;
                try {
                    path = FileSystems.getDefault().getPath("", imageURL);
                    Files.delete(path);
                } catch (NoSuchFileException x) {
                    System.err.format("%s: no such" + " file or directory%n", path);
                } catch (DirectoryNotEmptyException x) {
                    System.err.format("%s not empty%n", path);
                } catch (IOException x) {
                    // File permission problems are caught here.
                    System.err.println(x);
                }
            }
            
            logger.info(To[0]+":"+Subject+":"+H1+"Deleted."); 

        } catch (MessagingException e) {
            e.printStackTrace();
           // throw new RuntimeException(e);
             logger.info(To[0]+":"+Subject+":"+H1+"."+e.getMessage()); 
        }

        return "";
    }

     public static MimeMultipart getHTMLEmbdEmail(String H1, String[] imageURLs) throws MessagingException {
        //   This mail has 2 part, the BODY and the embedded image
        MimeMultipart multipart = new MimeMultipart("related");

        //   first part (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<H1>" + H1 + "</H1><img src=\"cid:image\">";
        messageBodyPart.setContent(htmlText, "text/html");
        //   add it
        multipart.addBodyPart(messageBodyPart);
        
        for(String imageURL : imageURLs)
        { 

        //   second part (the images)
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource(
                imageURL);

        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");
        messageBodyPart.setFileName(imageURL.contains(System.getProperty("file.separator"))?imageURL.substring(imageURL.lastIndexOf(System.getProperty("file.separator"))+1):imageURL);
        messageBodyPart.setDescription("Photo from mobile device.");
        //   add image to the multipart
        multipart.addBodyPart(messageBodyPart);
        }
        
        return multipart;
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
       SendEmail.sendAsyncEmail("Hello", new String[]{"manoj_nits@yahoo.com","azmechatech@gmail.com","hemuman@gmail.com"}, "Hello Manoj!", "MCW_RANK_1_.png");
       
       SendEmail.sendAsyncEmail("Hello", new String[]{"manoj_nits@yahoo.com","hemuman@gmail.com","azmechatech@gmail.com"}, "Hello Multi Manoj!", new String[]{"MCW_RANK_1_.png", "MCW_RANK_1_.png", "MCW_RANK_1_.png"});
       
       
        try {
            if( args.length>0)
            Thread.sleep(Integer.parseInt(args[0]));
            else
              Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendAsyncEmail(String Subject, String[] To, String H1, String imageURL) {
        Globals.fjPool.submit(new SendEmail(Subject, To, H1,imageURL));
    }
    
    public static void sendAsyncEmail(String Subject, String[] To, String H1, String[] imageURLs) {
        Globals.fjPool.submit(new SendEmail(Subject, To, H1,imageURLs));
    }

}

class Globals {
    static ForkJoinPool fjPool = new ForkJoinPool(8);
}
