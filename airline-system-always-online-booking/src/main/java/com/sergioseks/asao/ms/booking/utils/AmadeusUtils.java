package com.sergioseks.asao.ms.booking.utils;

import java.util.List;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightOrder;
import com.amadeus.resources.FlightPrice;
import com.amadeus.resources.Traveler;
import com.amadeus.resources.Traveler.Document;
import com.amadeus.resources.Traveler.Phone;
import com.google.gson.JsonObject;
import com.sergioseks.asao.ms.booking.dto.FlightOfferSearchDetailDTO;
import com.sergioseks.asao.ms.booking.dto.TravelerDTO;

public class AmadeusUtils {

	//private final static Amadeus amadeus = Amadeus.builder("YOUR_CLIENT_ID", "YOUR_CLIENT_SECRET").build();
	
	//private final static Amadeus amadeus = Amadeus.builder("OnztQYxN61WwJAGZSYOSGLR9j8joydNH", "qohrFS6jgAteQgvE").build();

	public static FlightPrice confirmFlightPrice(FlightOfferSearch flightOfferSearch, String clientId, String clientSecret) {

		FlightPrice flightPricing = null;

		try {
			
			Amadeus amadeus = Amadeus.builder(clientId, clientSecret).build();

			flightPricing = amadeus.shopping.flightOffersSearch.pricing.post(flightOfferSearch);

		} catch (ResponseException e) {

			e.printStackTrace();

		}

		return flightPricing;

	}

	public static FlightOrder confirmFlightOrder(FlightPrice flightPricing, Traveler[] travelerArray, String clientId, String clientSecret) {

		FlightOrder order = null;

		try {
			
			Amadeus amadeus = Amadeus.builder(clientId, clientSecret).build();

			order = amadeus.booking.flightOrders.post(flightPricing, travelerArray);

		} catch (ResponseException e) {

			e.printStackTrace();
		}

		return order;

	}

	public static FlightOfferSearch[] flightOfferSearches(FlightOfferSearchDetailDTO flightOfferSearchDetailDTO, String clientId, String clientSecret) {

		FlightOfferSearch[] flightOffersSearches = null;

		try {
			
			Amadeus amadeus = Amadeus.builder(clientId, clientSecret).build();

			flightOffersSearches = amadeus.shopping.flightOffersSearch
					.get(Params.with("originLocationCode", flightOfferSearchDetailDTO.getOriginLocationCode())
							.and("destinationLocationCode", flightOfferSearchDetailDTO.getDestinationLocationCode())
							.and("departureDate", flightOfferSearchDetailDTO.getDepartureDate())
							.and("returnDate", flightOfferSearchDetailDTO.getReturnDate())
							.and("adults", flightOfferSearchDetailDTO.getAdults())
							.and("children", flightOfferSearchDetailDTO.getChildren()));

		} catch (ResponseException e) {

			e.printStackTrace();
		}

		return flightOffersSearches;

	}

	public static Traveler[] generateTraveler(JsonObject consolidatedTraveler, List<TravelerDTO> travelers) {

		JsonObject jsonObjectConsolidatedTraveler = consolidatedTraveler.getAsJsonObject("data");

		Traveler traveler = new Traveler();

		traveler.setId("1");
		traveler.setDateOfBirth(jsonObjectConsolidatedTraveler.get("dateOfBirth").getAsString());
		traveler.setName(traveler.new Name(jsonObjectConsolidatedTraveler.get("firtsName").getAsString(),
				jsonObjectConsolidatedTraveler.get("lastName").getAsString()));

		Traveler.Phone[] phone = new Phone[1];

		phone[0] = traveler.new Phone();
		
		phone[0].setCountryCallingCode(jsonObjectConsolidatedTraveler.get("countryCallingCode").getAsString());
		phone[0].setNumber(jsonObjectConsolidatedTraveler.get("numberPhone").getAsString());
		phone[0].setDeviceType(jsonObjectConsolidatedTraveler.get("deviceType").getAsString());

		Traveler.Contact contact = traveler.new Contact();

		contact.setPhones(phone);
		
		traveler.setContact(contact);

		Traveler.Document[] document = new Document[1];

		document[0] = traveler.new Document();
		
		document[0].setDocumentType(jsonObjectConsolidatedTraveler.get("documentType").getAsString());
		document[0].setNumber(jsonObjectConsolidatedTraveler.get("numberDocument").getAsString());
		document[0].setExpiryDate(jsonObjectConsolidatedTraveler.get("expiryDate").getAsString());
		document[0].setIssuanceCountry(jsonObjectConsolidatedTraveler.get("issuanceCountry").getAsString());
		document[0].setNationality(jsonObjectConsolidatedTraveler.get("nationality").getAsString());
		document[0].setHolder(jsonObjectConsolidatedTraveler.get("holder").getAsBoolean());
		
		traveler.setDocuments(document);

		Traveler[] travelerArray = new Traveler[1];

		travelerArray[0] = traveler;

		System.out.println(travelerArray[0]);
		
		// coming soon add list sub travelers

		return travelerArray;
	}

}
