package com.sergioseks.asao.ms.checkin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.sergioseks.asao.ms.checkin.models.Checkin;
import com.sergioseks.asao.ms.checkin.services.implement.CheckinService;



@SpringBootTest(classes = AsaoMsCheckinApplication.class)
@TestMethodOrder(OrderAnnotation.class)
class AsaoMsCheckinApplicationTests {

	@Autowired
	private CheckinService checkinService;

	@Test
	@Order(1)
	void contextLoads() {
		assertThat(checkinService).isNotNull();
	}

	@Test
	@Order(2)
	void fetchAllCheckin() throws Exception {
		assertThat(checkinService.findAll()).isNotNull();
	}

	@Test
	@Order(3)
	void saveCheckin() throws Exception {

		Checkin checkin = new Checkin();
		
		checkin.setBookingId(2);
		checkin.setCheckinDate(new Date());
		checkin.setDepartureDate("LON");
		checkin.setDestination("WAS");
		checkin.setDocumentNumber("23456784");
		checkin.setId(1);
		checkin.setTravelerId(3);

		assertEquals(3, checkinService.save(checkin).getTravelerId());

	}

	@Test
	@Order(4)
	void findByCheckin() throws Exception {

		assertThat(checkinService.findById(3)).isNotNull();

	}

	@Test
	@Order(5)
	void deleteByCheckinId() throws Exception {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			checkinService.deleteById(3);
		});
		
	}

	@Test
	@Order(6)
	void deleteAll() throws Exception {

		checkinService.deleteAll();

		assertThat(checkinService.findAll()).isEmpty();

	}

}
