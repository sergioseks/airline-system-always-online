package com.sergioseks.asao.ms.payment.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sergioseks.asao.ms.payment.models.Payment;
import com.sergioseks.asao.ms.payment.repositories.IPaymentRepository;
import com.sergioseks.asao.ms.payment.services.IPaymentService;

@Service
public class PaymentService implements IPaymentService{

	@Autowired
	private IPaymentRepository iPaymentRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<Payment> findAll() throws Exception {
		
		return iPaymentRepository.findAll();
	}

	@Transactional
	@Override
	public Payment save(Payment t) throws Exception {

		return iPaymentRepository.save(t);
	}
	
	@Transactional
	@Override
	public Payment update(Payment t) throws Exception {

		return iPaymentRepository.save(t);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Payment> findById(Integer id) throws Exception {

		return iPaymentRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		
		iPaymentRepository.deleteById(id);
	}

	@Transactional
	@Override
	public void deleteAll() throws Exception {

		iPaymentRepository.deleteAll();		
	}

	@Transactional(readOnly = true)
	@Override
	public List<Payment> paymentByTravelerId(int travelerId) {

		return iPaymentRepository.paymentByTravelerId(travelerId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Payment> paymentByBookingId(int bookingId) {

		return iPaymentRepository.paymentByBookingId(bookingId);
	}

}
