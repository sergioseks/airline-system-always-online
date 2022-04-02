package com.sergioseks.asaomspayment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.sergioseks.asao.ms.payment.AsaoMsPaymentApplication;
import com.sergioseks.asao.ms.payment.models.Payment;
import com.sergioseks.asao.ms.payment.services.implement.PaymentService;


@SpringBootTest(classes = AsaoMsPaymentApplication.class)
@TestMethodOrder(OrderAnnotation.class)
class AsaoMsPaymentApplicationTests {

	@Autowired
	private PaymentService paymentService;

	@Test
	@Order(1)
	void contextLoads() {
		assertThat(paymentService).isNotNull();
	}

	@Test
	@Order(2)
	void fetchAllPayment() throws Exception {
		assertThat(paymentService.findAll()).isNotNull();
	}

	@Test
	@Order(3)
	void savePayment() throws Exception {

		Payment payment = new Payment();
		
		payment.setBookingId(2);
		payment.setDescription("Test description");
		payment.setState("payed");
		payment.setTravelerId(3);		

		assertEquals(3, paymentService.save(payment).getTravelerId());

	}

	@Test
	@Order(4)
	void findByPayment() throws Exception {

		assertThat(paymentService.findById(3)).isNotNull();

	}

	@Test
	@Order(5)
	void deleteByPaymentId() throws Exception {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			paymentService.deleteById(3);
		});
		
	}

	@Test
	@Order(6)
	void deleteAll() throws Exception {

		paymentService.deleteAll();

		assertThat(paymentService.findAll()).isEmpty();

	}

}
