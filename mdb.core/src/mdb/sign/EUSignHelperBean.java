/**
 * 
 */
package mdb.sign;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUser;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserSignInfo;
/**
 * @author azhuk
 * Creation date: Dec 18, 2014
 *
 */

@Stateless
public class EUSignHelperBean {
	private static final Logger _logger = Logger
			.getLogger(EUSignHelperBean.class.getCanonicalName());

	private EndUser sigLib = new EndUser(); 
	
	@PostConstruct
	private void initSignLib() {
		try {			
			sigLib.Initialize();
		} catch (Exception e) {		
			_logger.severe(e.getMessage());
		}
	}
	
	@PreDestroy
	private void finalizeSignLib() {
		sigLib.Finalize();
	}
	
	
	public String getHashData (byte[] data) {
		String toReturn = null;
		
		try {
			toReturn =sigLib.Hash(data);
		} catch (Exception e) {
			_logger.severe(e.getMessage());
		}
		
		return toReturn; 
	}
	
	@SuppressWarnings("finally")
	public String verify (String signature, String data) {
		String toReturn = null;
		StringBuilder sbuBuilder = new StringBuilder();
		 try {
			EndUserSignInfo signInfo = sigLib.Verify(signature, data);
			
			
			sbuBuilder.append("Підписувач: " + signInfo.GetOwnerInfo().GetSubjFullName());
			sbuBuilder.append("\n");
			sbuBuilder.append("Організація: " + signInfo.GetOwnerInfo().GetSubjOrg());
			sbuBuilder.append("\n");
			sbuBuilder.append("Підрозділ: " + signInfo.GetOwnerInfo().GetSubjOrgUnit());
			sbuBuilder.append("\n");
			sbuBuilder.append("Посада: " + signInfo.GetOwnerInfo().GetSubjTitle());
			sbuBuilder.append("\n");
			sbuBuilder.append("Сертіфікат: " + signInfo.GetOwnerInfo().GetIssuerCN());
			sbuBuilder.append("\n");
			sbuBuilder.append("Реєстраційний номер: " + signInfo.GetOwnerInfo().GetSerial());
			sbuBuilder.append("\n");
			sbuBuilder.append("Час підпису: " + signInfo.GetTimeInfo().GetTime());		
			
			/*
			sbuBuilder.append("GetSubjCN: " + signInfo.GetOwnerInfo().GetSubjCN());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetSubjDNS: " + signInfo.GetOwnerInfo().GetSubjDNS());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetSubjDRFOCode: " + signInfo.GetOwnerInfo().GetSubjDRFOCode());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetSubject: " + signInfo.GetOwnerInfo().GetSubject());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetSubjEDRPOUCode: " + signInfo.GetOwnerInfo().GetSubjEDRPOUCode());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetSubjEMail: " + signInfo.GetOwnerInfo().GetSubjEMail());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetSubjLocality: " + signInfo.GetOwnerInfo().GetSubjLocality());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetSubjLocality: " + signInfo.GetOwnerInfo().GetSubjLocality());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetSubjPhone: " + signInfo.GetOwnerInfo().GetSubjPhone());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetSubjState: " + signInfo.GetOwnerInfo().GetSubjState());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetSubjTitle: " + signInfo.GetOwnerInfo().GetSubjTitle());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetIssuer: " + signInfo.GetOwnerInfo().GetIssuer());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetIssuerCN: " + signInfo.GetOwnerInfo().GetIssuerCN());
			sbuBuilder.append("\n");
			
			sbuBuilder.append("GetSerial: " + signInfo.GetOwnerInfo().GetSerial());
			sbuBuilder.append("\n");
			sbuBuilder.append("GetSubjEMail: " + signInfo.GetOwnerInfo().GetSubjEMail());
			sbuBuilder.append("\n");
			
			*/		
			
			toReturn = sbuBuilder.toString();
			
			
			//signInfo.
		} catch (Exception e) {
			_logger.severe(e.getMessage());
			toReturn = e.getMessage();
		} 
		 finally {
			return toReturn;		 
		}
	}	
	
	
	
	
}
