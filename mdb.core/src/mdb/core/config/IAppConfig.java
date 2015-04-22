package mdb.core.config;


	
public interface IAppConfig {	
	
	public String getJndiContext ();
	public String getJndiJdbcContextName ();
	public String getJdbcDataSourceName ();
	public String GetUserName ();
	public String GetUserPwd();
	public boolean IsUsingQueryPool ();
	
}
