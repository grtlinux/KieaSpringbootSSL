package org.tain.config;

public class Env {

	private static String serverName;
	
	static {
		serverName = System.getenv("SERVER_NAME");
		if (serverName == null)
			serverName = "";
		
		System.out.println(">>>>> SERVER_NAME = " + getServerName());
	}
	
	public static String getServerName() {
		return serverName;
	}
}
