package com.sergioseks.asao.ms.payment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergioseks.asao.ms.payment.models.Payment;

public interface IPaymentRepository extends JpaRepository<Payment, Integer>{

	List<Payment> paymentByTravelerId(int travelerId);
	
	List<Payment> paymentByBookingId(int bookingId);
	
}
