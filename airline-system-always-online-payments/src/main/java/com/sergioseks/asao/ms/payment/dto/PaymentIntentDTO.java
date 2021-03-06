package com.sergioseks.asao.ms.payment.dto;

import java.io.Serializable;

public class PaymentIntentDTO implements Serializable {
	
	public enum Currency {
		USD, EUR;
	}

	private static final long serialVersionUID = 1L;
	
	private String description;
	
	private int amount;
	
	private Currency currency;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
