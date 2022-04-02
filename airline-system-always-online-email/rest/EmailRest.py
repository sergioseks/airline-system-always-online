from flask import Flask, jsonify, request
from injector import Injector
from pprint import pprint
from prometheus_flask_exporter import PrometheusMetrics
from services.EmailService import EmailService as emailService
from models.Email import Email as email
from dto.EmailDTO import EmailDTO as emailDTO
from datetime import date
from datetime import datetime

# Inject LogService dependency
ES = Injector().get(emailService)

# Inject LogDTO dependency
e_dto = Injector().get(emailDTO)

# Init flask app
app = Flask(__name__)

# Active prometheus metrics
PrometheusMetrics(app)


def get_flask_app():
    return app


def generate_dto(error_code, error_message, http_code, data) -> dict:
    e_dto.set_error_code(error_code)
    e_dto.set_error_message(error_message)
    e_dto.set_http_code(http_code)
    e_dto.set_data(data)

    return e_dto.email_dto_to_dict()


class EmailRest(object):

    @app.route("/")
    def index():
        return jsonify("Welcome from Email Microservice")

    @app.route("/emails", methods=["POST"])
    def send_email():

        # We use 'force' to skip mimetype checking to have shorter curl command.
        data = request.get_json(force=True)

        data["_id"] = "0"

        now = datetime.now()

        data["date"] = now

        global ES

        result = ES.send_email(email(data))

        dto_result = generate_dto("0", "Successful operation", "200", result)

        return jsonify(dto_result)
