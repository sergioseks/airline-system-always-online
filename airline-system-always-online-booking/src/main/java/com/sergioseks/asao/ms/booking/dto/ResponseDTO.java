package com.sergioseks.asao.ms.booking.dto;

import java.io.Serializable;

public class ResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer errorCode;
	private String errorMessage;
	private String httpCode;
	private String deployment;
	private String podName;
	private String podIp;
	private Object data;

	public ResponseDTO() {

	}

	public ResponseDTO(Integer errorCode, String errorMessage, String httpCode, String deployment, String podName,
			String podIp, Object data) {
		
		super();
		
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpCode = httpCode;
		this.deployment = deployment;
		this.podName = podName;
		this.podIp = podIp;
		this.data = data;
	}

	public String getDeployment() {
		return deployment;
	}

	public void setDeployment(String deployment) {
		this.deployment = deployment;
	}

	public String getPodName() {
		return podName;
	}

	public void setPodName(String podName) {
		this.podName = podName;
	}

	public String getPodIp() {
		return podIp;
	}

	public void setPodIp(String podIp) {
		this.podIp = podIp;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(String httpCode) {
		this.httpCode = httpCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
