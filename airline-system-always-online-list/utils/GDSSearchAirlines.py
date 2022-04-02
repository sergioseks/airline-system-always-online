from amadeus import Client, ResponseError, Location
from injector import inject
from models.Flight import Flight
import os


class GDSSearchAirlines(object):
    def __init__(self):
        self.client_id = '%s' % (os.environ['AMADEUS_CLIENT_ID'])
        self.client_secret = '%s' % (os.environ['AMADEUS_CLIENT_SECRET'])

        #self.__client = Client(
        #    client_id='OnztQYxN61WwJAGZSYOSGLR9j8joydNH',
        #    client_secret='qohrFS6jgAteQgvE'
        #)

        self.__client = Client(
            client_id=self.client_id,
            client_secret=self.client_secret
        )

    # Get the cheapest flights on a given journey
    @inject
    def flight_offers_search(self, kwargs: dict):

        flights_offers_returned = []

        try:
            response = self.__client.shopping.flight_offers_search.get(**kwargs)

            for flight in response.data:
                offer = Flight(flight).construct_flights()

                flights_offers_returned.append(offer)

            print(flights_offers_returned)

        except ResponseError as error:
            print(error)

        return flights_offers_returned

    # Return the flight most traveled
    @inject
    def flight_most_traveled_destinations(self, originCityCode: str, period: str):

        try:
            response = self.__client.travel.analytics.air_traffic.traveled.get(originCityCode=originCityCode,
                                                                               period=period)

            print(response)

        except ResponseError as error:
            print(error)

    # Returns details for a specific airport.
    @inject
    def get_locations(self, keyword: str):
        try:
            response = self.__client.reference_data.locations.get(
                keyword=keyword,
                subType=Location.ANY
            )

            print(response.body)  # => The raw response, as a string
            print(response.result)  # => The body parsed as JSON, if the result was parsable
            print(response.data)  # => The list of locations, extracted from the JSON

        except ResponseError as error:
            print(error)

    # Returns a list of relevant airports near to a given point.
    @inject
    def get_relevant_airports(self, longitude: float, latitude: float):
        try:
            response = self.__client.reference_data.locations.airports.get(
                longitude=longitude,
                latitude=latitude
            )

            print(response.body)  # => The raw response, as a string
            print(response.result)  # => The body parsed as JSON, if the result was parsable
            print(response.data)  # => The list of locations, extracted from the JSON

        except ResponseError as error:
            print(error)

    # Find the cheapest destinations where you can fly to.
    @inject
    def flight_destinations(self, origin: str):
        try:
            response = self.__client.shopping.flight_destinations.get(origin=origin)
            print(response.data)
        except ResponseError as error:
            print(error)
