/**
 * 
 */
package mdb.gateway.impl;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import mdb.core.shared.gw.mail.IMailGatewayServiceRemote;

/**
 * @author azhuk
 * Creation date: Jan 16, 2014
 *
 */

@Stateless
@LocalBean
@WebService
public class MailGateway implements IMailGatewayServiceRemote  {
	
	
	private static final Logger _logger = Logger.getLogger(MailGateway.class
			.getName());	 
	
	@Resource(lookup = "java:jboss/mail/bnppua")
	 private Session _mailSession;

	@Override
	@WebMethod	
     public void send(String addressFrom, String[] addressTo  ,String subject, String content)
       {
		try    {			
			MimeMessage m = new MimeMessage(_mailSession);
			Address from = new InternetAddress(addressFrom);			
				
			ArrayList<Address>  recipientsList = new ArrayList<Address>();
			for (String adrTo : addressTo) {
				recipientsList.add(new InternetAddress(adrTo));				
			}
			
			Address[] to  = new Address[recipientsList.size()]; 
			to= recipientsList.toArray(to);
			
			m.setHeader("Content-Type","text/plain; charset=\"utf-8\"");
			
			m.setFrom(from);
			
			m.setRecipients(Message.RecipientType.TO, to);
			m.setSubject(subject);
			m.setSentDate(new java.util.Date());			
			m.setContent(content,"text/plain; charset=utf-8");
			
			//MimeMessage.setContentLanguage(languages);
			Transport.send(m);
			_logger.info("Mail Sent Successfully.");
			}
		catch (javax.mail.MessagingException e)
			{		
				_logger.severe("Error in Sending Mail: "+e);
			}
	}            
     
}
