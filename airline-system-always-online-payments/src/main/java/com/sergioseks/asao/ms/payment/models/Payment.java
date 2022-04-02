package com.sergioseks.asao.ms.payment.models;

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
@Table(name = "payment")
@NamedQueries({
	@NamedQuery(name="Payment.paymentByTravelerId",
			query="select p from Payment p where p.travelerId=?1"),
	@NamedQuery(name="Payment.paymentByBookingId",
	query="select p from Payment p where p.bookingId=?1")
})
public class Payment implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column( name = "traveler_id" )
	private int travelerId;
	
	@Column( name = "booking_id" )
	private int bookingId;
	
	@Column( name = "description" )
	private String description;
	
	@Column( name = "payment_date" )
	private Date paymentDate;
	
	@Column( name = "state" )
	private String state;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
