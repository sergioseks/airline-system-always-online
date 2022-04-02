from flask import Flask, jsonify, request
from flask_cors import CORS
from injector import Injector
from amadeus import ResponseError
from prometheus_flask_exporter import PrometheusMetrics
from services.ListService import ListService as listService
from services.RedisService import RedisService as redisService
from dto.ListDTO import ListDTO as listDTO
import json

# Inject ListService dependency
LS = Injector().get(listService)

RS = Injector().get(redisService)

# Inject ListDTO dependency
l_dto = Injector().get(listDTO)

# Init flask app
app = Flask(__name__)

# Active CORS
CORS(app)

# Active prometheus metrics
PrometheusMetrics(app)


def get_flask_app():
    return app


def generate_dto(error_code, error_message, http_code, data) -> dict:

    l_dto.set_error_code(error_code)
    l_dto.set_error_message(error_message)
    l_dto.set_http_code(http_code)
    l_dto.set_data(data)

    return l_dto.flight_dto_to_dict()


class ListRest(object):

    @app.route("/")
    def index():
        return jsonify("Welcome from List Microservice")

    @app.route("/flights", methods=["POST"])
    def get_flights():

        dto_result = None

        cache_response = RS.exists_value("flights")

        if cache_response:

            print("In Redis")

            return jsonify(json.loads(RS.get_value("flights")))

        try:

            global LS

            # We use 'force' to skip mimetype checking to have shorter curl command.
            data = request.get_json(force=True)

            result = LS.search_cheapest(dict(data))

            dto_result = generate_dto("0", "Successful operation", "200", result)

            if len(result) > 0:
                RS.set_value("flights", json.dumps(dto_result))

            print("Out Redis")

        except ResponseError as error:
            print(error)

        return jsonify(dto_result)
