from injector import Injector, inject
from utils.GDSSearchAirlines import GDSSearchAirlines


class ListService(object):
    def __init__(self):
        self.gds_connector = Injector().get(GDSSearchAirlines)

    @inject
    def search_cheapest(self, args: dict):
        return self.gds_connector.flight_offers_search(args)

    @inject
    def flight_most_traveled(self, originCityCode: str, period: str):
        self.gds_connector.flight_most_traveled_destinations(originCityCode, period)

    @inject
    def details_specific_airport(self, keyword: str):
        self.gds_connector.get_locations(keyword)

    @inject
    def relevant_airport(self, longitude: float, latitude: float):
        self.gds_connector.get_relevant_airports(longitude, latitude)

    @inject
    def cheapest_destinations(self, origin: str):
        self.gds_connector.flight_destinations(origin)
