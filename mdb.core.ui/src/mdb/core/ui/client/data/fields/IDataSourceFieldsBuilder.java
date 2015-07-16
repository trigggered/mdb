/**
 * 
 */
package mdb.core.ui.client.data.fields;

import java.util.logging.Level;
import java.util.logging.Logger;

import mdb.core.shared.transport.IRequestData;
import mdb.core.ui.client.communication.IDataProvider;

/**
 * @author azhuk
 * Creation date: Jul 16, 2015
 *
 */
public interface IDataSourceFieldsBuilder {
	public final static String FLD_ENTITY_DESC_KEY = "FLD_ENTITY_DESC_KEY_";
	
	public   void createFields (IDataProvider dataProvider, IRequestData entity);
	public 	 void createDefaultFields ( IDataProvider  dataProvider, IRequestData entity );		
	
}
