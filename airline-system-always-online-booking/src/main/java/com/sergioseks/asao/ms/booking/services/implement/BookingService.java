package com.sergioseks.asao.ms.booking.services.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sergioseks.asao.ms.booking.models.Booking;
import com.sergioseks.asao.ms.booking.respositories.BookingRespository;
import com.sergioseks.asao.ms.booking.services.IBookingService;

@Service
public class BookingService implements IBookingService {

	@Autowired
	private BookingRespository productRepository;

	@Transactional(readOnly = true)
	@Override
	public List<Booking> findAll() throws Exception {

		return productRepository.findAll();
	}

	@Transactional
	@Override
	public Booking save(Booking booking) throws Exception {

		return productRepository.save(booking);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Booking> findById(Integer id) throws Exception {

		return productRepository.findById(id);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {

		productRepository.deleteById(id);
	}

	@Transactional
	@Override
	public void deleteAll() throws Exception {
		
		productRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Booking> bookingByTravelerId(int travelerId) {

		return productRepository.bookingByTravelerId(travelerId);
	}

}
