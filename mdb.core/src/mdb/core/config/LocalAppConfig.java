package mdb.core.config;


public class LocalAppConfig implements IAppConfig {

	final String UNAME ="root";
	final String UPWD ="www";
	
	@Override
	public String getJndiContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJndiJdbcContextName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getJdbcDataSourceName() {
		return "com.mysql.jdbc.Driver";
	}

	@Override
	public String GetUserName() {
		return UNAME;
	}

	@Override
	public String GetUserPwd() {
		return UPWD;
	}

	@Override
	public boolean IsUsingQueryPool() {
		return true;
	}
	

}
