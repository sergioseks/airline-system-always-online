package com.sergioseks.asao.ms.booking.services;

import java.util.List;

import com.sergioseks.asao.ms.booking.models.Booking;

public interface IBookingService extends ICRUDService<Booking> {

	List<Booking> bookingByTravelerId(int travelerId);
}
