package com.sergioseks.asao.ms.payment.services;

import java.util.List;

import com.sergioseks.asao.ms.payment.models.Payment;

public interface IPaymentService extends ICRUDService<Payment> {

	List<Payment> paymentByTravelerId(int travelerId);
	
	List<Payment> paymentByBookingId(int bookingId);
	
}
