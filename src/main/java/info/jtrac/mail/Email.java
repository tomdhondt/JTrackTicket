package main.java.info.jtrac.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import main.java.info.jtrac.domain.Item;

public class Email{
	/* Instance members */
	private String email_To;
	private String email_From;
	private String email_Subject;
	private static final String userName ="tom_dhont@hotmail.com";
	private static final String passWord ="Studiotek*1*";
	/**
	 * Default constructor for the class
	 */
	public Email(){
	}
	/*
	 * Method will initialize the mailProperties 
	 */
	private Properties getProperties(){
		Properties properties = new Properties(System.getProperties());
		properties.put("mail.smtp.host", "smtp.live.com");
		properties.put("mail.smtp.socketFactory.port", "25");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "25");
		properties.put("mail.smtp.starttls.enable", "true");
		return properties;
	}
	/*
	 * Method will initialize the Session
	 */
	private Session getSession(Properties properties, Authenticator authenticator){
		return Session.getInstance(properties);
	}
	/*
	 * Method will initialize the Authenticator
	 */
	private Authenticator getAuthenticator(){
		Authenticator authenticator = new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(userName, passWord);
            }
        };
		return authenticator;
	}
	/**
	 * Method will send a mail regarding the status of a Item
	 * @param item as Item
	 * @return boolean as success rate
	 */
	public boolean sendItemStatusMail(Item item){
		boolean success = false;
        try {
            Message message = new MimeMessage(this.getSession(this.getProperties(), this.getAuthenticator()));
            message.setFrom(new InternetAddress(this.email_From));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(this.email_To));
            message.setSubject(this.email_Subject);
            if(null != item){
            	message.setContent(this.createItemStatusMailBody(item), "text/html");
            	Transport.send(message);
            	success = true;
            	System.out.println("mail sended successfully ...");
            }
		} catch (MessagingException e) {
		}
        return success;
	}
	/**
	 * Method will create the mail to update the participants
	 * @param item as Item
	 * @return String as messageContent, Null when the Item is null
	 */
	private String  createItemStatusMailBody(Item item){
		String mailMessage = null;
		if(null != item){
			StringBuilder mail = new StringBuilder();
			mail.append("	<div class=\"ITEMHEADER\">");
			mail.append("		<p>Dear,</p>");
			mail.append("		<p>There is a new ticket added.  Please find bellow the details of the added Item.</p>");
			mail.append("	</div>");
			mail.append("	<div class=\"ITEMDETAIL\">");
			mail.append("		<table class=\"tableDetail\" style=\"width=100%\" id=\"01\">");
			mail.append("			<tr>");
			mail.append("				<td class=\"tableDetail\" style=\"border: 1px solid black; width:15%\">ITEM ID</td>");
			mail.append("				<td class=\"tableDetail\" style=\"border: 1px solid black; width:35%\">" + item.getId() + "</td>");
			mail.append("				<td class=\"tableDetail\" style=\"border: 1px solid black; width:15%\">Created at</td>");
			mail.append("				<td class=\"tableDetail\" style=\"border: 1px solid black; width:35%\">" + item.getTimeStamp() + "</td>");
			mail.append("			</tr>");
			mail.append("			<tr>");
			mail.append("				<td class=\"tableDetail\" style=\"border: 1px solid black; width:15%\">ITEM Title</td>");
			mail.append("				<td class=\"tableDetail\" style=\"border: 1px solid black; width:85%\" COLSPAN=3>" + item.getSummary() + "</td>");
			mail.append("			</tr>");
			mail.append("			<tr>");
			mail.append("				<td class=\"tableDetail\" style=\"border: 1px solid black; width:15%\">ITEM DESCRIPTION</td>");
			mail.append("				<td class=\"tableDetail\" style=\"border: 1px solid black; width:85%\" COLSPAN=3>" + item.getDetail() + "</td>");
			mail.append("			</tr>");
			mail.append("			<tr>");
			mail.append("				<td class=\"tableDetail\" style=\"border: 1px solid black; width:15%\">ITEM STATE</td>");
			mail.append("				<td class=\"tableDetail\" style=\"border: 1px solid black; width:35%\">" + item.getStatus() + "</td>");
			mail.append("				<td class=\"tableDetail\" style=\"border: 1px solid black; width:15%\">ALLOCATED TO</td>");
			mail.append("				<td class=\"tableDetail\" style=\"border: 1px solid black; width:35%\">" + item.getAssignedTo().getName() + "</td>");
			mail.append("			</tr>");
			mail.append("		</table>");
			mail.append("	<div>");
			mail.append("	<div class=\"ITEMLINK\">");
			mail.append("		<p>You can add extra information consurning this item at the JTrack Interface <a href=\"http://localhost:8080/JTrackWebInterface/#!JTrackTicketView\">hier</a></p>");
			mail.append("	<div>");
			mail.append("	<div class=\"ITEMCONTACT\">");
			mail.append("		<table>");
			mail.append("			<tr>");
			mail.append("				<td style=\"width:100%\">JTrack Ticket Team</td>");
			mail.append("			</tr>");
			mail.append("			<tr>");
			mail.append("				<td style=\"width:100%\">tel: +(32) 458 25 62 30</td>");
			mail.append("			</tr>");
			mail.append("			<tr>");
			mail.append("				<td style=\"width:100%\">e-mail <a href=\"mailto:jtrackticketteam@dhondt.be\">help@jtrack.be</a></td>");
			mail.append("			</tr>");
			mail.append("		</table>");
			mail.append("	</div>");
			mailMessage = mail.toString();
		}
		return mailMessage;
	}
}
