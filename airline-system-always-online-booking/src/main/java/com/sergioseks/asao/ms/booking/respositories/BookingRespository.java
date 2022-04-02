package com.sergioseks.asao.ms.booking.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sergioseks.asao.ms.booking.models.Booking;

@Repository
public interface BookingRespository extends JpaRepository<Booking, Integer>{

	List<Booking> bookingByTravelerId(int travelerId);
}
