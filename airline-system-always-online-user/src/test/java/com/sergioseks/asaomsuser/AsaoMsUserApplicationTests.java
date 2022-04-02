package com.sergioseks.asaomsuser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.sergioseks.asao.ms.user.AsaoMsUserApplication;
import com.sergioseks.asao.ms.user.models.Document;
import com.sergioseks.asao.ms.user.models.Phone;
import com.sergioseks.asao.ms.user.models.User;
import com.sergioseks.asao.ms.user.services.implement.DocumentService;
import com.sergioseks.asao.ms.user.services.implement.PhoneService;
import com.sergioseks.asao.ms.user.services.implement.UserService;


@SpringBootTest(classes = AsaoMsUserApplication.class)
@TestMethodOrder(OrderAnnotation.class)
class AsaoMsUserApplicationTests {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private PhoneService phoneService;

	@Test
	@Order(1)
	void contextLoads() {
		assertThat(userService).isNotNull();
	}

	@Test
	@Order(2)
	void fetchAllUsers() throws Exception {
		assertThat(userService.findAll()).isNotNull();
	}

	@Test
	@Order(3)
	void saveUser() throws Exception {

		User user =  new User();
		
		user.setId(1);
		user.setDateOfBirth("1994-08-30");
		user.setEmail("test email");
		user.setFirtsName("Sergio");
		user.setLastName("Espinal");
		user.setPasssword("1234");

		assertThat(userService.save(user)).isNotNull();

	}
	
	@Test
	@Order(4)
	void saveDocument() throws Exception {
		
		User user =  new User();
		
		user.setId(3);
		user.setDateOfBirth("1994-08-30");
		user.setEmail("test email");
		user.setFirtsName("Sergio");
		user.setLastName("Espinal");
		user.setPasssword("1234");

		Document document = new Document();
		
		document.setId(1);
		document.setDocumentType("passport");
		document.setExpiryDate("2022-07-01");
		document.setHolder(true);
		document.setIssuanceCountry("");
		document.setNationality("PE");
		document.setNumber("3490837");
		document.setUser(user);

		assertThat(documentService.save(document)).isNotNull();

	}

	@Test
	@Order(5)
	void savePhone() throws Exception {
		
		User user =  new User();
		
		user.setId(3);
		user.setDateOfBirth("1994-08-30");
		user.setEmail("test email");
		user.setFirtsName("Sergio");
		user.setLastName("Espinal");
		user.setPasssword("1234");


		Phone phone = new Phone();
		
		phone.setId(1);
		phone.setCountryCallingCode("");
		phone.setDeviceType("MOBILE");
		phone.setNumber("987632424");
		phone.setUser(user);

		assertThat(phoneService.save(phone)).isNotNull();

	}
	
	@Test
	@Order(6)
	void findByUser() throws Exception {

		assertThat(userService.findById(3)).isNotNull();

	}

	@Test
	@Order(7)
	void deleteByUserId() throws Exception {

		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			userService.deleteById(3);
		});
		
	}

	@Test
	@Order(8)
	void deleteAll() throws Exception {

		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			userService.deleteAll();
		});

	}
}
