/**
 * 
 */
package mdb.core.vaadin.ui.communication;

import java.util.logging.Logger;

import mdb.core.ui.client.communication.IDataProvider;
import mdb.core.ui.client.communication.impl.AMdbEntityDataProvider;
import mdb.core.ui.client.data.fields.IDataSourceFieldsBuilder;
import mdb.core.vaadin.ui.data.fields.DataSourceFieldsBuilder;

/**
 * @author azhuk
 * Creation date: Jul 16, 2015
 *
 */
public class MdbEntityDataProvider extends AMdbEntityDataProvider {
	private static final Logger _logger = Logger
			.getLogger(MdbEntityDataProvider.class.getName());

	/* (non-Javadoc)
	 * @see mdb.core.ui.client.communication.impl.AMdbEntityDataProvider#getDataSourceFieldsBuilder()
	 */
	@Override
	protected IDataSourceFieldsBuilder getDataSourceFieldsBuilder() {
		
		return  DataSourceFieldsBuilder.instance();
	}
	
	public static IDataProvider create() {
		return new MdbEntityDataProvider();
	}
}
