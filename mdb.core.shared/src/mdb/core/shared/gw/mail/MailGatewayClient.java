/**
 * 
 */
package mdb.core.shared.gw.mail;


import mdb.core.shared.gw.WebServiceClient;

/**
 * @author azhuk
 * Creation date: Oct 8, 2014
 *
 */
public class MailGatewayClient extends WebServiceClient<IMailGatewayServiceRemote>  
		implements IMailGatewayServiceRemote {

	
	/**
	 * @param serviceClass
	 * @param webSrvName
	 */
	public MailGatewayClient(Class<IMailGatewayServiceRemote> serviceClass,
			String webSrvName) {
		super(serviceClass, webSrvName);
	}

	/* (non-Javadoc)
	 * @see mdb.core.shared.gw.WebServiceClient#getNamespace()
	 */
	@Override
	protected String getNamespace() {
		return "http://impl.gateway.mdb/";
	}

	/* (non-Javadoc)
	 * @see mdb.core.shared.gw.mail.IMailGatewayServiceRemote#send(java.lang.String, java.lang.String[], java.lang.String, java.lang.String)
	 */
	@Override
	public void send(String addressFrom, String[] addressTo, String subject,
			String content) {
		getWebClientProxy().send(addressFrom, addressTo, subject, content);		
	}
	
}
