package com.sergioseks.asao.ms.booking.rest;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightOrder;
import com.amadeus.resources.FlightPrice;
import com.amadeus.resources.Traveler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sergioseks.asao.ms.booking.dto.BookingDTO;
import com.sergioseks.asao.ms.booking.dto.FlightOfferSearchDetailDTO;
import com.sergioseks.asao.ms.booking.dto.ResponseDTO;
import com.sergioseks.asao.ms.booking.models.Booking;
import com.sergioseks.asao.ms.booking.services.implement.BookingService;
import com.sergioseks.asao.ms.booking.utils.AmadeusUtils;
import com.sergioseks.asao.ms.booking.utils.ServerDetail;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	private static final Logger LOG = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private BookingService bookingService;

	@Autowired
	@Qualifier("beanServerDetail")
	private ServerDetail serverDetail;
	
	@Value("${userurl}")
	private String userUrl;
	
	@Value("${amadeus.clientid}")
	private String clientId;
	
	@Value("${amadeus.clientsecret}")
	private String clientSecret;

	@ApiOperation(notes = "Return all bookings", value = "booking", nickname = "booking")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Booking.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthBookings() throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		List<Booking> bookings = new ArrayList<>();

		try {

			bookings = bookingService.findAll();

			response.setErrorCode(0);
			response.setData(bookings);
			response.setErrorMessage("Successfully operation: fetch all bookings");
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

	@ApiOperation(notes = "Return save booking", value = "booking", nickname = "booking")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Booking.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> saveBooking(@RequestBody BookingDTO bookingDTO) throws UnknownHostException {

		ResponseDTO responseDTO = new ResponseDTO();

		FlightPrice flightPricing = null;

		FlightOrder order = null;

		FlightOfferSearch[] flightOffersSearches = null;

		ResteasyClient client = new ResteasyClientBuilder().build();

		try {

			Response clientResponse = client
					.target(userUrl + "/users/" + Integer.toString(bookingDTO.getTraveler_id())
							+ "/documents/" + Integer.toString(bookingDTO.getDocument_id()) + "/phones/"
							+ Integer.toString(bookingDTO.getPhone_id()))
					.request(MediaType.APPLICATION_JSON_VALUE).get();

			String responseString = clientResponse.readEntity(String.class);

			clientResponse.close();

			JsonObject responseJson = JsonParser.parseString(responseString).getAsJsonObject();

			// create flight offer search detail dto
			FlightOfferSearchDetailDTO flightOfferSearchDetailDTO = new FlightOfferSearchDetailDTO();

			flightOfferSearchDetailDTO.setOriginLocationCode(bookingDTO.getOriginLocationCode());
			flightOfferSearchDetailDTO.setDestinationLocationCode(bookingDTO.getDestinationLocationCode());
			flightOfferSearchDetailDTO.setDepartureDate(bookingDTO.getDepartureDate());
			flightOfferSearchDetailDTO.setReturnDate(bookingDTO.getReturnDate());
			flightOfferSearchDetailDTO.setAdults(bookingDTO.getAdults());
			flightOfferSearchDetailDTO.setChildren(bookingDTO.getChildren());

			// get all flight offers
			flightOffersSearches = AmadeusUtils.flightOfferSearches(flightOfferSearchDetailDTO, clientId, clientSecret);
			
			System.out.println(flightOffersSearches[3].getSource());

			if (flightOffersSearches != null) {

				// confirm flight price in amadeus system
				flightPricing = AmadeusUtils.confirmFlightPrice(flightOffersSearches[bookingDTO.getSelectedPosition()], clientId, clientSecret);

				if (flightPricing != null) {

					Traveler[] traverler = AmadeusUtils.generateTraveler(responseJson, bookingDTO.getTravelers());

					order = AmadeusUtils.confirmFlightOrder(flightPricing, traverler, clientId, clientSecret);

				}

			}

			if (order == null) {

				responseDTO.setErrorCode(2);
				responseDTO.setData(null);
				responseDTO.setErrorMessage("Error operation: The booking could not be generated");
				responseDTO.setHttpCode("" + HttpStatus.OK.value());
				responseDTO.setDeployment(serverDetail.getDeploy());
				responseDTO.setPodName(serverDetail.getPodName());
				responseDTO.setPodIp(serverDetail.getPodIp());

				return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);

			}

			Booking booking = new Booking();

			booking.setTravelerId(bookingDTO.getTraveler_id());
			booking.setOriginLocationCode(bookingDTO.getOriginLocationCode());
			booking.setDestinationLocationCode(bookingDTO.getDestinationLocationCode());
			booking.setDepartureDate(bookingDTO.getDepartureDate());
			booking.setReturnDate(bookingDTO.getReturnDate());
			booking.setAdults(bookingDTO.getAdults());
			booking.setChilds(bookingDTO.getChildren());
			booking.setTotalPrice(bookingDTO.getTotalPrice());
			booking.setFirstFlightAirline(bookingDTO.getFirstFlightAirline());
			
			Calendar calendar = Calendar.getInstance();

			calendar.setTime(new Date());

			calendar.add(Calendar.HOUR, -5);
			
			booking.setBookedDate(calendar.getTime());

			Booking bookingData = bookingService.save(booking);

			responseDTO.setErrorCode(0);
			responseDTO.setData(bookingData);
			responseDTO.setErrorMessage("Successfully operation: save booking");
			responseDTO.setHttpCode("" + HttpStatus.OK.value());
			responseDTO.setDeployment(serverDetail.getDeploy());
			responseDTO.setPodName(serverDetail.getPodName());
			responseDTO.setPodIp(serverDetail.getPodIp());

			System.out.println(order.getResponse());

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

	@ApiOperation(notes = "Return booking by id", value = "booking", nickname = "booking")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Booking.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthBookingById(@PathVariable("id") int id) throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			Optional<Booking> bookingById = bookingService.findById(id);

			if (bookingById.isPresent()) {

				response.setErrorCode(0);
				response.setData(bookingById);
				response.setErrorMessage("Successfully operation: fetch booking by id");
				response.setHttpCode("" + HttpStatus.OK.value());
				response.setDeployment(serverDetail.getDeploy());
				response.setPodName(serverDetail.getPodName());
				response.setPodIp(serverDetail.getPodIp());

			} else {

				response.setErrorCode(0);
				response.setData(bookingById);
				response.setErrorMessage("Successfully operation: Not found booking");
				response.setHttpCode("" + HttpStatus.NOT_FOUND.value());
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
	
	@ApiOperation(notes = "Return booking by id", value = "booking", nickname = "booking")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Booking.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@GetMapping(value = "/travelers/{travelerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> fecthBookingByTravelerId(@PathVariable("travelerId") int travelerId) throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			List<Booking> bookings = bookingService.bookingByTravelerId(travelerId);

			if (!bookings.isEmpty()) {

				response.setErrorCode(0);
				response.setData(bookings);
				response.setErrorMessage("Successfully operation: fetch booking by id");
				response.setHttpCode("" + HttpStatus.OK.value());
				response.setDeployment(serverDetail.getDeploy());
				response.setPodName(serverDetail.getPodName());
				response.setPodIp(serverDetail.getPodIp());

			} else {

				response.setErrorCode(0);
				response.setData(bookings);
				response.setErrorMessage("Successfully operation: Not found booking");
				response.setHttpCode("" + HttpStatus.NOT_FOUND.value());
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

	@ApiOperation(notes = "Delete booking by id", value = "booking", nickname = "booking")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Booking.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> deleteBookingById(@PathVariable("id") int id) throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			bookingService.deleteById(id);

			response.setErrorCode(0);
			response.setData(null);
			response.setErrorMessage("Successfully operation: delete booking");
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

	@ApiOperation(notes = "Delete all bookings", value = "booking", nickname = "booking")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = Booking.class),
			@ApiResponse(code = 400, message = "Bad Request") })
	@DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDTO> deleteBooking() throws UnknownHostException {

		ResponseDTO response = new ResponseDTO();

		try {

			bookingService.deleteAll();

			response.setErrorCode(0);
			response.setData(null);
			response.setErrorMessage("Successfully operation: delete all booking");
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

}
