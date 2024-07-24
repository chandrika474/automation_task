package org.deloitte.utils;

	import java.io.File;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import jakarta.activation.DataHandler;
	import jakarta.activation.FileDataSource;
	import jakarta.mail.Authenticator;
	import jakarta.mail.BodyPart;
	import jakarta.mail.Message;
	import jakarta.mail.MessagingException;
	import jakarta.mail.Multipart;
	import jakarta.mail.PasswordAuthentication;
	import jakarta.mail.Session;
	import jakarta.mail.Transport;
	import jakarta.mail.internet.InternetAddress;
	import jakarta.mail.internet.MimeBodyPart;
	import jakarta.mail.internet.MimeMessage;
	import jakarta.mail.internet.MimeMultipart;

	public class SendMail {
	    private String to = TestData.testConfig.getToEmail();  // Recipient's email
	    private String from = TestData.testConfig.getFromEmail();   // Sender's email
	    private String host = TestData.testConfig.getHostEmail();     // SMTP server
	    private final String username = TestData.testConfig.getLoginEmail();  // SMTP username
	    private final String password = TestData.testConfig.getLoginEmailPassword();   // SMTP password

	    private static final String REPORT_DIR = TestData.rootDir + TestData.separator+"HtmlReport"+TestData.separator+"ExtentHtml.html";

	    public void sendEmail(String reportFileName) {
	        File file = new File(REPORT_DIR + reportFileName);
	        if (!file.exists() || file.length() == 0) {
	            System.err.println("File not found or is empty: " + file.getAbsolutePath());
	            return;
	        }

	        Properties properties = new Properties();
	        properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", "587");
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");

	        Session session = Session.getInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });

	        try {
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(from));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
	            message.setSubject("Extent Reports For Test Result");

	            BodyPart messageBodyPart = new MimeBodyPart();
	            messageBodyPart.setText("Dear Sir/Madam,\nPlease find the attached report.");

	            Multipart multipart = new MimeMultipart();
	            multipart.addBodyPart(messageBodyPart);

	            messageBodyPart = new MimeBodyPart();
	            FileDataSource source = new FileDataSource(file);
	            messageBodyPart.setDataHandler(new DataHandler(source));
	            messageBodyPart.setFileName(file.getName());
	            multipart.addBodyPart(messageBodyPart);

	            message.setContent(multipart);

	            Transport.send(message);
	            System.out.println("Sent message successfully with attachment: " + file.getName());

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }

	    public void sendEmailWithLatestReport() {
	        // Wait for the report to be generated
	        waitForReportGeneration(REPORT_DIR);

	        // Find the latest report file
	        File reportDir = new File(REPORT_DIR);
	        File[] files = reportDir.listFiles(file -> file.getName().endsWith(".html") || file.getName().endsWith(".pdf"));

	        if (files != null && files.length > 0) {
	            // Sort files by last modified time
	            File latestFile = files[0];
	            for (File file : files) {
	                if (file.lastModified() > latestFile.lastModified()) {
	                    latestFile = file;
	                }
	            }

	            // Send the email with the latest report attachment
	            sendEmail(latestFile.getName());
	        } else {
	            System.err.println("No report files found in the directory.");
	        }
	    }

	    private void waitForReportGeneration(String reportDirPath) {
	        File reportDir = new File(reportDirPath);
	        long startTime = System.currentTimeMillis();
	        long timeout = TimeUnit.MINUTES.toMillis(5); // Adjust timeout as needed

	        while (System.currentTimeMillis() - startTime < timeout) {
	            File[] files = reportDir.listFiles(file -> file.getName().endsWith(".html") || file.getName().endsWith(".pdf"));

	            if (files != null && files.length > 0) {
	                // Check if the files are updated recently
	                boolean allFilesUpdated = Arrays.stream(files)
	                                                .allMatch(file -> file.lastModified() > startTime);

	                if (allFilesUpdated) {
	                    return; // Exit if files are updated
	                }
	            }

	            try {
	                Thread.sleep(5000); // Wait before checking again
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	            }
	        }

	        System.err.println("Report generation timeout reached.");
	    }
	    
	    public static void main(String args[]) {
	    	new SendMail().sendEmailWithLatestReport();
	    }
	}

