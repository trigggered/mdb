package mdb.core.config;


public class LocalAppConfig implements IAppConfig {

	final String UNAME ="P00090730";
	final String UPWD  ="P00090730";
	
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
		//return "com.oracle.jdbc.Driver";
		return "oracle.jdbc.OracleDriver";
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
