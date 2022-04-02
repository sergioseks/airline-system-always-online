package com.sergioseks.asao.ms.payment.rest;

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

import com.sergioseks.asao.ms.payment.dto.PaymentIntentDTO;
import com.sergioseks.asao.ms.payment.dto.ResponseDTO;
import com.sergioseks.asao.ms.payment.models.Payment;
import com.sergioseks.asao.ms.payment.services.implement.PaymentIntentService;
import com.sergioseks.asao.ms.payment.services.implement.PaymentService;
import com.sergioseks.asao.ms.payment.utils.ServerDetail;
import com.stripe.model.PaymentIntent;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private PaymentIntentService paymentIntentService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	@Qualifier("beanServerDetail")
	private ServerDetail serverDetail;

	@ApiOperation(notes = "Create a payment", value = "payment", nickname = "payment")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = PaymentIntentDTO.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> createPayment(@RequestBody PaymentIntentDTO paymentIntentDTO)
			throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			PaymentIntent paymentIntent = paymentIntentService.paymentIntent(paymentIntentDTO);

			String paymentString = paymentIntent.toJson();

			response.setErrorCode(0);
			response.setData(paymentString);
			response.setErrorMessage("Successfully operation: Create a payment");
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

	@ApiOperation(notes = "Confirm a payment by id", value = "payment", nickname = "payment")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = PaymentIntentDTO.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping(value = "/confirm/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> confirmPayment(@PathVariable("id") String id) throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			PaymentIntent paymentIntent = paymentIntentService.confirm(id);

			String paymentString = paymentIntent.toJson();

			response.setErrorCode(0);
			response.setData(paymentString);
			response.setErrorMessage("Successfully operation: Confirm a payment");
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

	@ApiOperation(notes = "Cancel a payment by id", value = "payment", nickname = "payment")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = PaymentIntentDTO.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping(value = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> cancelPayment(@PathVariable("id") String id) throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			PaymentIntent paymentIntent = paymentIntentService.cancel(id);

			String paymentString = paymentIntent.toJson();

			response.setErrorCode(0);
			response.setData(paymentString);
			response.setErrorMessage("Successfully operation: Cancel a payment");
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

	@ApiOperation(notes = "Return all payment", value = "payment", nickname = "payment")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Payment.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthPayments() throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		List<Payment> payments = new ArrayList<>();

		try {

			payments = paymentService.findAll();

			response.setErrorCode(0);
			response.setData(payments);
			response.setErrorMessage("Successfully operation: fetch all payments");
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

	@ApiOperation(notes = "Return save payment", value = "payment", nickname = "payment")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Payment.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> savePayment(@RequestBody Payment payment) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			Calendar calendar = Calendar.getInstance();

			calendar.setTime(new Date());

			calendar.add(Calendar.HOUR, -5);
			
			payment.setPaymentDate(calendar.getTime());

			Payment paymentData = paymentService.save(payment);

			responseDTO.setErrorCode(0);
			responseDTO.setData(paymentData);
			responseDTO.setErrorMessage("Successfully operation: save payemnt");
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

	@ApiOperation(notes = "Return update payment", value = "payment", nickname = "payment")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Payment.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> updatePayment(@RequestBody Payment payment) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			Calendar calendar = Calendar.getInstance();

			calendar.setTime(new Date());

			calendar.add(Calendar.HOUR, -5);
			
			payment.setPaymentDate(calendar.getTime());

			Payment paymentData = paymentService.save(payment);

			responseDTO.setErrorCode(0);
			responseDTO.setData(paymentData);
			responseDTO.setErrorMessage("Successfully operation: update payment");
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

	@ApiOperation(notes = "Return payment by id", value = "payment", nickname = "payment")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Payment.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthPaymentById(@PathVariable("id") int id) throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			Optional<Payment> payment = paymentService.findById(id);

			if (payment.isPresent()) {

				response.setErrorCode(0);
				response.setData(payment);
				response.setErrorMessage("Successfully operation: fetch payment by id");
				response.setHttpCode("" + HttpStatus.OK.value());
				response.setDeployment(serverDetail.getDeploy());
				response.setPodName(serverDetail.getPodName());
				response.setPodIp(serverDetail.getPodIp());

			} else {

				response.setErrorCode(0);
				response.setData(payment);
				response.setErrorMessage("Successfully operation: not found payment");
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

	@ApiOperation(notes = "Return payment by traveler id", value = "payment", nickname = "payment")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Payment.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(value = "/travelers/{travelerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthPaymentByTravelerId(@PathVariable("travelerId") int travelerId)
			throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			List<Payment> payments = paymentService.paymentByTravelerId(travelerId);

			if (!payments.isEmpty()) {

				response.setErrorCode(0);
				response.setData(payments);
				response.setErrorMessage("Successfully operation: fetch payment by traveler id");
				response.setHttpCode("" + HttpStatus.OK.value());
				response.setDeployment(serverDetail.getDeploy());
				response.setPodName(serverDetail.getPodName());
				response.setPodIp(serverDetail.getPodIp());

			} else {

				response.setErrorCode(0);
				response.setData(payments);
				response.setErrorMessage("Successfully operation: not found payment by traveler id");
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

	@ApiOperation(notes = "Return payment by booking id", value = "payment", nickname = "payment")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Payment.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(value = "/bookings/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthPaymentBybookingId(@PathVariable("bookingId") int bookingId)
			throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			List<Payment> payments = paymentService.paymentByBookingId(bookingId);

			if (!payments.isEmpty()) {

				response.setErrorCode(0);
				response.setData(payments);
				response.setErrorMessage("Successfully operation: fetch payment by booking id");
				response.setHttpCode("" + HttpStatus.OK.value());
				response.setDeployment(serverDetail.getDeploy());
				response.setPodName(serverDetail.getPodName());
				response.setPodIp(serverDetail.getPodIp());

			} else {

				response.setErrorCode(0);
				response.setData(payments);
				response.setErrorMessage("Successfully operation: not found payment by booking id");
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

	@ApiOperation(notes = "Delete payment by id", value = "payment", nickname = "payment")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Payment.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> deletePaymentById(@PathVariable("id") int id) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			paymentService.deleteById(id);

			responseDTO.setErrorCode(0);
			responseDTO.setData(null);
			responseDTO.setErrorMessage("Successfully operation: delete payment by id");
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

	@ApiOperation(notes = "Delete all payment", value = "payment", nickname = "payment")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Payment.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> deletePayment() throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		try {

			paymentService.deleteAll();

			responseDTO.setErrorCode(0);
			responseDTO.setData(null);
			responseDTO.setErrorMessage("Successfully operation: delete all payment");
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
