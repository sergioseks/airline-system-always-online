package com.sergioseks.asao.ms.booking.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

@Component("beanServerDetail")
public class ServerDetail {
	
	private String hostAddress;
	private String hostname;
	private String[] deployType;

	public String getPodName() throws UnknownHostException {
	    // Local host name
		hostname = InetAddress.getLocalHost().getHostName();
	    
		return hostname;
	}
	
	public String getPodIp() throws UnknownHostException {
		// Local address
		hostAddress = InetAddress.getLocalHost().getHostAddress();
		
		return hostAddress;
	}
	
	public String getDeploy() throws UnknownHostException {
		//Local deployment name
		String hostNameDeploy = InetAddress.getLocalHost().getHostName();
		
		deployType = hostNameDeploy.split("-");
		
		return deployType[3];
	}
}
