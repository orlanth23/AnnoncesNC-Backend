package com.oliweb.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendMail {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private SendMail() {

    }

    public static void executeTLS(final PropertiesMail propertiesMail, String to, String object, String text) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", propertiesMail.getHost());
		props.put("mail.smtp.host", propertiesMail.getHost());
		props.put("mail.smtp.port", propertiesMail.getPort());

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(propertiesMail.getUsername(), propertiesMail.getPassword());
                    }
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(propertiesMail.getFrom()));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(object);
			message.setText(text);
			Transport.send(message);
		} catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
	}

	public static void executeSSL(final PropertiesMail propertiesMail, String to, String object, String text) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(propertiesMail.getUsername(), propertiesMail.getPassword());
                    }
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(propertiesMail.getFrom()));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(object);
			message.setText(text + "SSL");
			Transport.send(message);
		} catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
	}
}
