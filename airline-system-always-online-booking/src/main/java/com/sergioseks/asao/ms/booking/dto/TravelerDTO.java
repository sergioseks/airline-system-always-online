package com.sergioseks.asao.ms.booking.dto;

import java.io.Serializable;

public class TravelerDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// personal info
	private String dateOfBirth;

	private String firtsName;

	private String lastName;

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirtsName() {
		return firtsName;
	}

	public void setFirtsName(String firtsName) {
		this.firtsName = firtsName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
