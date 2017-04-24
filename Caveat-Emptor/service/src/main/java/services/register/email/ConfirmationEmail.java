package services.register.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.RegistrationDto;

public class ConfirmationEmail {

	public static final String MAIL_REGISTRATION_SITE_LINK = "http://localhost:8080/Web/enableUser.xhtml";

	public static void sendEmail(RegistrationDto registration) {

		Properties properties = getProperties();
		Session session = createSession(properties);

		try {

			Message message = writeEmail(registration, session);

			try {
				Transport.send(message);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			System.out.println("Sent message successfully....");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	private static Session createSession(Properties properties) {
		// Get the default Session object.
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(properties.getProperty("username"),
						properties.getProperty("password"));
			}
		});
		return session;
	}

	private static Message writeEmail(RegistrationDto registration, Session session)
			throws MessagingException, AddressException {

		String link = MAIL_REGISTRATION_SITE_LINK + "?scope=activation&userId=" + registration.getUser().getId()
				+ "&code=" + registration.getValidationCode();

		Message message = new MimeMessage(session);
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(registration.getUser().getEmail()));
		message.setSubject("Confirmation email @ Caveat Emptor");
		message.setText("Dear User," + "\n\n Please click on the following link for the account confirmation! \n\n "
				+ link + "\n\n Thanks, \n Delia");
		return message;
	}

	private static Properties getProperties() {
		Properties properties = new Properties();
		InputStream input = null;

		try {

			String filename = "config.properties";
			input = ConfirmationEmail.class.getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
			}

			properties.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}

}
