package com.sergioseks.asao.ms.checkin.rest;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.sergioseks.asao.ms.checkin.dto.ResponseDTO;
import com.sergioseks.asao.ms.checkin.models.Checkin;
import com.sergioseks.asao.ms.checkin.services.implement.CheckinService;
import com.sergioseks.asao.ms.checkin.utils.ServerDetail;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/checkins")
public class CheckinController {

	private static final Logger LOG = LoggerFactory.getLogger(CheckinController.class);

	@Autowired
	private CheckinService checkinService;

	@Autowired
	@Qualifier("beanServerDetail")
	private ServerDetail serverDetail;

	@ApiOperation(notes = "Return all checkins", value = "checkins", nickname = "checkins")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Checkin.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthCheckins() throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		List<Checkin> checkins = new ArrayList<>();

		try {

			checkins = checkinService.findAll();

			response.setErrorCode(0);
			response.setData(checkins);
			response.setErrorMessage("Successfully operation: fetch all checkins");
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

	@ApiOperation(notes = "Return save checkin", value = "checkin", nickname = "checkin")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Checkin.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> saveCheckin(@RequestBody Checkin checkin) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {
			
			Calendar calendar = Calendar.getInstance();

			calendar.setTime(new Date());

			calendar.add(Calendar.HOUR, -5);
			
			checkin.setCheckinDate(calendar.getTime());

			Checkin checkinData = checkinService.save(checkin);

			responseDTO.setErrorCode(0);
			responseDTO.setData(checkinData);
			responseDTO.setErrorMessage("Successfully operation: save checkin");
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

	@ApiOperation(notes = "Return update checkin", value = "checkin", nickname = "checkin")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Checkin.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> updateCheckin(@RequestBody Checkin checkin) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			Calendar calendar = Calendar.getInstance();

			calendar.setTime(new Date());

			calendar.add(Calendar.HOUR, -5);
			
			checkin.setCheckinDate(calendar.getTime());
			
			Checkin checkinData = checkinService.save(checkin);

			responseDTO.setErrorCode(0);
			responseDTO.setData(checkinData);
			responseDTO.setErrorMessage("Successfully operation: update checkin");
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

	@ApiOperation(notes = "Return checkin by id", value = "checkin", nickname = "checkin")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Checkin.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthCheckinById(@PathVariable("id") int id) throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			Optional<Checkin> checkin = checkinService.findById(id);

			if (checkin.isPresent()) {

				response.setErrorCode(0);
				response.setData(checkin);
				response.setErrorMessage("Successfully operation: fetch checkin by id");
				response.setHttpCode("" + HttpStatus.OK.value());
				response.setDeployment(serverDetail.getDeploy());
				response.setPodName(serverDetail.getPodName());
				response.setPodIp(serverDetail.getPodIp());

			} else {

				response.setErrorCode(0);
				response.setData(checkin);
				response.setErrorMessage("Successfully operation: not found checkin");
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
	
	@ApiOperation(notes = "Return checkin by id", value = "checkin", nickname = "checkin")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Checkin.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(value = "/travelers/{travelerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthCheckinTravelerById(@PathVariable("travelerId") int travelerId) throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			List<Checkin> checkins = checkinService.checkinByTravelerId(travelerId);

			if (!checkins.isEmpty()) {

				response.setErrorCode(0);
				response.setData(checkins);
				response.setErrorMessage("Successfully operation: fetch checkin by traveler id");
				response.setHttpCode("" + HttpStatus.OK.value());
				response.setDeployment(serverDetail.getDeploy());
				response.setPodName(serverDetail.getPodName());
				response.setPodIp(serverDetail.getPodIp());

			} else {

				response.setErrorCode(0);
				response.setData(checkins);
				response.setErrorMessage("Successfully operation: not found checkin by traveler id");
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
	
	@ApiOperation(notes = "Delete checkin by id", value = "checkin", nickname = "checkin")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Checkin.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> deleteCheckinById(@PathVariable("id") int id) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			checkinService.deleteById(id);

			responseDTO.setErrorCode(0);
			responseDTO.setData(null);
			responseDTO.setErrorMessage("Successfully operation: delete checkin by id");
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
	
	@ApiOperation(notes = "Delete all checkins", value = "checkins", nickname = "checkins")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Checkin.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> deleteCheckin() throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			checkinService.deleteAll();

			responseDTO.setErrorCode(0);
			responseDTO.setData(null);
			responseDTO.setErrorMessage("Successfully operation: delete all checkins");
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
