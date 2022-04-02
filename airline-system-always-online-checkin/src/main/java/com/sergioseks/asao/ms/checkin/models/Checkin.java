package com.sergioseks.asao.ms.checkin.models;

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
@Table(name = "checkin")
@NamedQueries({
	@NamedQuery(name="Checkin.checkinByTravelerId",
			query="select c from Checkin c where c.travelerId=?1")
})
public class Checkin implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "traveler_id")
	private int travelerId;
	
	@Column(name = "booking_id")
	private int bookingId;
	
	@Column(name = "document_number")
	private String documentNumber;
	
	@Column(name = "destination")
	private String destination;
	
	@Column(name = "departure_date")
	private String departureDate;
	
	@Column(name = "checkin_date")
	private Date checkinDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTravelerId() {
		return travelerId;
	}

	public void setTravelerId(int travelerId) {
		this.travelerId = travelerId;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public Date getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}
	
}
