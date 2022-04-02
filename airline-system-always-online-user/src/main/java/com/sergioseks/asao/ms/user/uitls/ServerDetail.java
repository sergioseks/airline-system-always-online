package com.sergioseks.asao.ms.user.uitls;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Component;

@Component("beanServerDetail")
public class ServerDetail {
	
	private String hostAddress;
	private String hostname;
	private String[] deployType;

	public String getPodName() throws UnknownHostException {
	    // Local address
		hostname = InetAddress.getLocalHost().getHostName();
	    
		return hostname;
	}
	
	public String getPodIp() throws UnknownHostException {
		// Local address
		hostAddress = InetAddress.getLocalHost().getHostAddress();
		return hostAddress;
	}
	
	public String getDeploy() throws UnknownHostException {
		String hostnameDeploy = InetAddress.getLocalHost().getHostName();
		deployType = hostnameDeploy.split("-");
		return deployType[3];
	}
}
