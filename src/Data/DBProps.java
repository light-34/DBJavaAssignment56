package Data;

import java.util.Properties;

public class DBProps { 	
	private static Properties connectionProps = new Properties();	
	public static Properties getConnectionProps() {
		connectionProps.put("user" , "n01379944");
		connectionProps.put("password" , "oracle");
		return connectionProps;
	}	
	public static String getDBURL() {
		connectionProps.put("dbURL" , "jdbc:oracle:thin:@calvin.humber.ca:1521:grok");
		return connectionProps.getProperty("dbURL");
	}
}
