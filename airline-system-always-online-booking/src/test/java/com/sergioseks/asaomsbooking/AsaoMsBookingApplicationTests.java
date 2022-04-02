package com.sergioseks.asaomsbooking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.sergioseks.asao.ms.booking.AsaoMsBookingApplication;
import com.sergioseks.asao.ms.booking.models.Booking;
import com.sergioseks.asao.ms.booking.services.implement.BookingService;

@SpringBootTest(classes = AsaoMsBookingApplication.class)
@TestMethodOrder(OrderAnnotation.class)
class AsaoMsBookingApplicationTests {

	@Autowired
	private BookingService bookingService;

	@Test
	@Order(1)
	void contextLoads() {
		assertThat(bookingService).isNotNull();
	}

	@Test
	@Order(2)
	void fetchAllBookings() throws Exception {
		assertThat(bookingService.findAll()).isNotNull();
	}

	@Test
	@Order(3)
	void saveBooking() throws Exception {

		Booking booking = new Booking();

		booking.setId(3);
		booking.setTravelerId(3);
		booking.setOriginLocationCode("LON");
		booking.setDestinationLocationCode("WAS");
		booking.setFirstFlightAirline("Test Firts Airlines");
		booking.setDepartureDate("2020-07-04");
		booking.setReturnDate("2020-07-09");
		booking.setTotalPrice(359.00);
		booking.setAdults(1);
		booking.setChilds(0);

		assertEquals(3, bookingService.save(booking).getTravelerId());

	}

	@Test
	@Order(4)
	void findByBooking() throws Exception {

		assertThat(bookingService.findById(3)).isNotNull();

	}

	@Test
	@Order(5)
	void deleteByBooking() throws Exception {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			bookingService.deleteById(3);
		});
		
	}

	@Test
	@Order(6)
	void deleteAll() throws Exception {

		bookingService.deleteAll();

		assertThat(bookingService.findAll()).isEmpty();

	}

}
