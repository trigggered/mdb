package mdb.core.config;

public class JBossConfig implements IAppConfig {
	

	@Override
	public String getJndiContext() {		
		return "java:jboss/datasources";
	}

	@Override
	public String getJndiJdbcContextName() {		
		return "java:jboss/datasources/";
	}

	@Override
	public String getJdbcDataSourceName() {	
		return "MySqlDS";
	}

	@Override
	public String GetUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String GetUserPwd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean IsUsingQueryPool() {
		return true;
	}

}
