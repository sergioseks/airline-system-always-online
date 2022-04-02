from flask import Flask, jsonify, request
from injector import Injector
from pprint import pprint
from pymongo import errors as mongoerrors
from prometheus_flask_exporter import PrometheusMetrics
from services.LogService import LogService as logService
from models.Log import Log as log
from dto.LogDTO import LogDTO as logDTO
from datetime import date
from datetime import datetime

# Inject LogService dependency
LS = Injector().get(logService)

# Inject LogDTO dependency
lo_dto = Injector().get(logDTO)

# Init flask app
app = Flask(__name__)

# Active prometheus metrics
PrometheusMetrics(app)


def get_flask_app():
    return app


def generate_dto(error_code, error_message, http_code, data) -> dict:
    lo_dto.set_error_code(error_code)
    lo_dto.set_error_message(error_message)
    lo_dto.set_http_code(http_code)
    lo_dto.set_data(data)

    return lo_dto.log_dto_to_dict()


class LogRest(object):

    @app.route("/")
    def index():
        return jsonify("Welcome from Log Microservice")

    @app.route("/logs", methods=["GET"])
    def get_all():
        dto_result = None

        try:
            global LS
            result = LS.get_all()

            dto_result = generate_dto("0", "Successful operation", "200", result)

        except mongoerrors:
            pprint(mongoerrors.__dict__.keys())

        return jsonify(dto_result)

    @app.route("/logs", methods=["POST"])
    def save():
        dto_result = None

        try:
            # We use 'force' to skip mimetype checking to have shorter curl command.
            data = request.get_json(force=True)

            data["_id"] = "0"

            now = datetime.now()

            data["date"] = now

            global LS
            result = LS.save(log(data))

            dto_result = generate_dto("0", "Successful operation", "200", result)

        except mongoerrors:
            pprint(mongoerrors.__dict__.keys())

        return jsonify(dto_result)

    @app.route("/logs", methods=["PUT"])
    def update():
        dto_result = None

        try:
            data = request.get_json(force=True)

            now = datetime.now()

            data["date"] = now

            global LS
            result = LS.update(log(data))

            dto_result = generate_dto("0", "Successful operation", "200", result)

        except mongoerrors:
            pprint(mongoerrors.__dict__.keys())

        return jsonify(dto_result)

    @app.route("/logs/<log_id>", methods=["GET"])
    def get_by_id(log_id):
        dto_result = None

        try:
            global LS
            result = LS.get_by_id(log_id)

            if result is not None:
                result["_id"] = str(result["_id"])
                dto_result = generate_dto("0", "Successful operation", "200", result)
            else:
                dto_result = generate_dto("1", "Not Found", "404", {})

        except mongoerrors:
            pprint(mongoerrors.__dict__.keys())

        return jsonify(dto_result)

    @app.route("/logs/<log_id>", methods=["DELETE"])
    def remove_by_id(log_id):
        dto_result = None

        try:
            global LS
            LS.remove_by_id(log_id)

            dto_result = generate_dto("0", "Successful operation", "200", {})

        except mongoerrors:
            pprint(mongoerrors.__dict__.keys())

        return jsonify(dto_result)

    @app.route("/logs", methods=["DELETE"])
    def remove_all():
        dto_result = None

        try:
            global LS
            LS.remove_all()

            dto_result = generate_dto("0", "Successful operation", "200", {})

        except mongoerrors:
            pprint(mongoerrors.__dict__.keys())

        return jsonify(dto_result)
