package com.sergioseks.asao.ms.user.rest;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergioseks.asao.ms.user.uitls.ServerDetail;


@RestController
public class MainController {
	
	@Autowired
	@Qualifier("beanServerDetail")
	private ServerDetail serverDetail;
	
	@GetMapping("/")
	public String index() throws UnknownHostException {
		
		return "Welcome to the users ms, pod name: " + serverDetail.getPodName() + ", pod ip: " + serverDetail.getPodIp();
	}
	
}
