package com.sergioseks.asao.ms.user.rest;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergioseks.asao.ms.user.dto.ResponseDTO;
import com.sergioseks.asao.ms.user.models.Phone;
import com.sergioseks.asao.ms.user.services.implement.PhoneService;
import com.sergioseks.asao.ms.user.uitls.ServerDetail;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/users/phones")
public class PhoneController {

	private static final Logger LOG = LoggerFactory.getLogger(PhoneController.class);

	@Autowired
	private PhoneService phoneService;

	@Autowired
	@Qualifier("beanServerDetail")
	private ServerDetail serverDetail;

	@ApiOperation(notes = "Return all phones", value = "phones", nickname = "phones")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Phone.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthPhones() throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		List<Phone> phones = new ArrayList<>();

		try {

			phones = phoneService.findAll();

			response.setErrorCode(0);
			response.setData(phones);
			response.setErrorMessage("Successfully operation: fetch all phones");
			response.setHttpCode("" + HttpStatus.OK.value());
			response.setDeployment(serverDetail.getDeploy());
			response.setPodName(serverDetail.getPodName());
			response.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			response.setErrorCode(1);
			response.setErrorMessage(e.getLocalizedMessage());
			response.setDeployment(serverDetail.getDeploy());
			response.setPodName(serverDetail.getPodName());
			response.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(notes = "Return save phone", value = "phone", nickname = "phone")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Phone.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> savePhone(@RequestBody Phone phone) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			Phone phoneData = phoneService.save(phone);

			responseDTO.setErrorCode(0);
			responseDTO.setData(phoneData);
			responseDTO.setErrorMessage("Successfully operation: save phone");
			responseDTO.setHttpCode("" + HttpStatus.OK.value());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			responseDTO.setErrorCode(1);
			responseDTO.setErrorMessage(e.getLocalizedMessage());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(notes = "Return update phone", value = "phone", nickname = "phone")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Phone.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> updatePhone(@RequestBody Phone phone) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			Phone phoneData = phoneService.save(phone);

			responseDTO.setErrorCode(0);
			responseDTO.setData(phoneData);
			responseDTO.setErrorMessage("Successfully operation: update phone");
			responseDTO.setHttpCode("" + HttpStatus.OK.value());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			responseDTO.setErrorCode(1);
			responseDTO.setErrorMessage(e.getLocalizedMessage());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(notes = "Return phone by id", value = "phone", nickname = "phone")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Phone.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthPhoneById(@PathVariable("id") int id) throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			Optional<Phone> phone = phoneService.findById(id);

			if (phone.isPresent()) {

				response.setErrorCode(0);
				response.setData(phone);
				response.setErrorMessage("Successfully operation: fetch phone by id");
				response.setHttpCode("" + HttpStatus.OK.value());
				response.setDeployment(serverDetail.getDeploy());
				response.setPodName(serverDetail.getPodName());
				response.setPodIp(serverDetail.getPodIp());

			} else {

				response.setErrorCode(0);
				response.setData(phone);
				response.setErrorMessage("Successfully operation: not found phone");
				response.setHttpCode("" + HttpStatus.OK.value());
				response.setDeployment(serverDetail.getDeploy());
				response.setPodName(serverDetail.getPodName());
				response.setPodIp(serverDetail.getPodIp());

			}

			return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			response.setErrorCode(1);
			response.setErrorMessage(e.getLocalizedMessage());
			response.setDeployment(serverDetail.getDeploy());
			response.setPodName(serverDetail.getPodName());
			response.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(notes = "Delete phone by id", value = "phone", nickname = "phone")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Phone.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> deletePhoneById(@PathVariable("id") int id) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			phoneService.deleteById(id);

			responseDTO.setErrorCode(0);
			responseDTO.setData(null);
			responseDTO.setErrorMessage("Successfully operation: delete phone by id");
			responseDTO.setHttpCode("" + HttpStatus.OK.value());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			responseDTO.setErrorCode(1);
			responseDTO.setErrorMessage(e.getLocalizedMessage());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(notes = "Delete all phones", value = "phones", nickname = "phones")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Phone.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> deletePhones() throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			phoneService.deleteAll();

			responseDTO.setErrorCode(0);
			responseDTO.setData(null);
			responseDTO.setErrorMessage("Successfully operation: delete all phones");
			responseDTO.setHttpCode("" + HttpStatus.OK.value());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

		} catch (Exception e) {

			LOG.error(e.getLocalizedMessage());

			responseDTO.setErrorCode(1);
			responseDTO.setErrorMessage(e.getLocalizedMessage());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
