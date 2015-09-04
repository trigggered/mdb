/**
 * 
 */
package mdb.core.shared.transformation.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @author azhuk
 * Creation date: Jul 24, 2013
 *
 */
public class XsltTransformation {
	
	private static final Logger _logger = Logger
			.getLogger(XsltTransformation.class.getName());
	
	private static TransformerFactory _factory;
	
	
	static {
		_factory = TransformerFactory.newInstance();
	}
	
	public static String transform(String source, String transformXslt, Map<String,String> parameters) {		
		InputStream isXslt = new ByteArrayInputStream(transformXslt.getBytes());		
		return transform (source, isXslt, parameters);
	}
	
	
	
	public static String transform(String source, InputStream transformXslt, Map<String,String> parameters) {
		return  transform(new ByteArrayInputStream(source.getBytes()),  transformXslt, parameters);		
	}

	public static String transform(InputStream isSource, InputStream transformXslt, Map<String,String> parameters) {						
				
		Source xslt = new StreamSource(transformXslt);        
		Transformer transformer = null;
		try {
			transformer = _factory.newTransformer(xslt);
			
		} catch (TransformerConfigurationException e1) {
			_logger.severe(e1.getMessage());
		}

		
		if(parameters != null) {			
			for  ( Entry<String, String> set :  parameters.entrySet() ) {
				transformer.setParameter(set.getKey(), set.getValue());
			}
		}
		
        Source text = new StreamSource(isSource);
        
        
        StringWriter writer = new StringWriter(); 
        try {
			transformer.transform(text, new StreamResult(writer));
		} catch (TransformerException e) {
			_logger.severe(e.getMessage());
		}
        
        
		return writer.toString();
	}


}
