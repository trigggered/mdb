package mdb.core.config;

public class OraJBossConfig extends JBossConfig {	
	
	@Override
	public String getJdbcDataSourceName() {	
		return "OraDS";
	}
	
}
