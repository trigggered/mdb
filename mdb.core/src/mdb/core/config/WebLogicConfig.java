package mdb.core.config;


public class WebLogicConfig implements IAppConfig {

	final String WL_JNDI_CONTEXT = "weblogic.jndi.WLInitialContextFactory";
	final String JNDI_JDBC_CTX_NAME =	"java:comp/env/jdbc/";	
	final String DATA_SOURCE		= "ABSGT";
	
	@Override
	public String getJndiContext() {
		return WL_JNDI_CONTEXT;
	}

	@Override
	public String getJndiJdbcContextName() {
		return JNDI_JDBC_CTX_NAME;
	}

	@Override
	public String getJdbcDataSourceName() {
		return DATA_SOURCE;
	}

	@Override
	public String GetUserName() {
		return "mdb";
	}

	@Override
	public String GetUserPwd() {
		return "mdb";
	}

	@Override
	public boolean IsUsingQueryPool() {
		return true;
	}
	
	

}
