package com.sergioseks.asao.ms.booking.dto;

import java.io.Serializable;
import java.util.List;

public class BookingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int traveler_id;

	private int document_id;

	private int phone_id;

	private int selectedPosition;

	private String originLocationCode;

	private String destinationLocationCode;

	private String departureDate;

	private String returnDate;

	private int adults;

	private int children;

	private double totalPrice;

	private String firstFlightAirline;

	private List<TravelerDTO> travelers;

	public int getTraveler_id() {
		return traveler_id;
	}

	public void setTraveler_id(int traveler_id) {
		this.traveler_id = traveler_id;
	}

	public int getDocument_id() {
		return document_id;
	}

	public void setDocument_id(int document_id) {
		this.document_id = document_id;
	}

	public int getPhone_id() {
		return phone_id;
	}

	public void setPhone_id(int phone_id) {
		this.phone_id = phone_id;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

	public String getOriginLocationCode() {
		return originLocationCode;
	}

	public void setOriginLocationCode(String originLocationCode) {
		this.originLocationCode = originLocationCode;
	}

	public String getDestinationLocationCode() {
		return destinationLocationCode;
	}

	public void setDestinationLocationCode(String destinationLocationCode) {
		this.destinationLocationCode = destinationLocationCode;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public int getAdults() {
		return adults;
	}

	public void setAdults(int adults) {
		this.adults = adults;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getFirstFlightAirline() {
		return firstFlightAirline;
	}

	public void setFirstFlightAirline(String firstFlightAirline) {
		this.firstFlightAirline = firstFlightAirline;
	}

	public List<TravelerDTO> getTravelers() {
		return travelers;
	}

	public void setTravelers(List<TravelerDTO> travelers) {
		this.travelers = travelers;
	}

}
