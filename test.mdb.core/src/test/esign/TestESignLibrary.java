/**
 * 
 */
package test.esign;

import java.util.logging.Logger;

import org.junit.Test;

import com.iit.certificateAuthority.endUser.libraries.signJava.EndUser;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserCertificateInfo;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserKeyMedia;
import com.iit.certificateAuthority.endUser.libraries.signJava.EndUserKeyMediaSettings;

/**
 * @author azhuk
 * Creation date: Nov 12, 2014
 *
 */
public class TestESignLibrary {
	private static final Logger _logger = Logger
			.getLogger(TestESignLibrary.class.getCanonicalName());
	
	@Test 
	public void testHash() {
		EndUser endUser = new EndUser();
		
		String data = "qwerty";
		String hashdata = null;
		try {
			
			EndUserKeyMedia keyMedia = new EndUserKeyMedia(5, 2, "Test_Qwerty1"); 
			EndUserKeyMediaSettings keyMediaSettings = endUser.CreateKeyMediaSettings();
			
			keyMediaSettings.SetSourceType(2);
			keyMediaSettings.SetShowErrors(true);
			keyMediaSettings.SetKeyMedia(keyMedia);
			
			endUser.Initialize();
			//endUser.SetKeyMediaSettings(keyMediaSettings);
			
			
			_logger.info("SourceType " + endUser.GetKeyMediaSettings().GetSourceType());
			
			_logger.info("TypeIndex " + endUser.GetKeyMediaSettings().GetKeyMedia().GetTypeIndex());
			_logger.info("DevIndex " + endUser.GetKeyMediaSettings().GetKeyMedia().GetDevIndex());
			_logger.info("Password " + endUser.GetKeyMediaSettings().GetKeyMedia().GetPassword());
						
			
			endUser.ParseCertificate(endUser.GetOwnCertificate()).GetSubjUserCode();
			endUser.ParseCertificate(endUser.GetOwnCertificate()).GetSerial();
			//EndUserOwnerInfo owi  = endUser.GetPrivateKeyOwnerInfo();						
			
			EndUserCertificateInfo sertInfo =  endUser.ParseCertificate(endUser.GetOwnCertificate());
			
			_logger.info("SubjUserCode = " +sertInfo.GetSubjUserCode() );
			
			//EndUserCertificateInfo sert = endUser.GetCertificateInfo(null, null);
			//sert.GetSubjUserCode()			
	
			//endUser.ResetOperation();
			//endUser.ReadPrivateKey();
			hashdata = endUser.Hash(data);
			
			_logger.info(hashdata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			endUser.Finalize();
		}
		
	}
}
