package com.sergioseks.asao.ms.booking.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "booking")
@NamedQueries({
	@NamedQuery(name="Booking.bookingByTravelerId",
			query="select b from Booking b where b.travelerId=?1")
})
public class Booking implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "booked_date")
	private Date bookedDate;
	
	@Column(name = "traveler_id")
	private int travelerId;

	@Column(name = "origin_location_code")
	private String originLocationCode;

	@Column(name = "destination_location_code")
	private String destinationLocationCode;

	@Column(name = "departure_date")
	private String departureDate;

	@Column(name = "return_date")
	private String returnDate;

	@Column(name = "adults")
	private int adults;

	@Column(name = "childs")
	private int childs;
	
	@Column(name = "total_price")
	private double totalPrice;
	
	@Column(name = "first_flight_airline")
	private String firstFlightAirline;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(Date bookedDate) {
		this.bookedDate = bookedDate;
	}

	public int getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(int traveler_id) {
		this.travelerId = traveler_id;
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

	public int getChilds() {
		return childs;
	}

	public void setChilds(int childs) {
		this.childs = childs;
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

}
