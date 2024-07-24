package org.deloitte.utils;

import java.io.File;
import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailSender {
	
	private String to = TestData.testConfig.getToEmail();  // Recipient's email
	private String from = TestData.testConfig.getFromEmail();   // Sender's email
	private String host = TestData.testConfig.getHostEmail();     // Gmail SMTP server
	private final String username = TestData.testConfig.getLoginEmail();  // SMTP username
	private final String password = TestData.testConfig.getLoginEmailPassword();   // SMTP password
	private static final String REPORT_PATH = TestData.rootDir + TestData.separator+"HtmlReport"+TestData.separator+"ExtentHtml.html";
    	
	public void sendEmail() {
        
       
	//	 String filePaths = "D:\\JavaFiles\\task_automation_main\\Reports\\Spark.html";
        
        File file = new File(REPORT_PATH);
        if (!file.exists()) {
            System.err.println("File not found: " + REPORT_PATH);
            return;
        }

        // Setup mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);  // SMTP server
        properties.put("mail.smtp.port", "587"); // SMTP port
        properties.put("mail.smtp.auth", "true"); // Enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS
      /**properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.transport.protocol", "SMTP"); **/
       
        
        // Get the Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
        	protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Extent Reports For Test Result");
            
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Dear Sir/Madam ,"
            		+ 						" \n Please find The below Attachaments");   

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
          //  FileDataSource source = new FileDataSource(filePath);
            FileDataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
          // messageBodyPart.setFileName(filePath);
            messageBodyPart.setFileName(file.getName());
            multipart.addBodyPart(messageBodyPart);

     /**       messageBodyPart = new MimeBodyPart();
            //  FileDataSource source = new FileDataSource(filePath);
              FileDataSource sources = new FileDataSource(filePaths);
              messageBodyPart.setDataHandler(new DataHandler(sources));
              messageBodyPart.setFileName(filePaths);
             // messageBodyPart.setFileName(file.getName());
              multipart.addBodyPart(messageBodyPart);  **/
            
            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully with attachment: " + file.getName());
            
            
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    
    	}
	
	public static void main(String args[]) {
		new EmailSender().sendEmail();
		
	}
}
