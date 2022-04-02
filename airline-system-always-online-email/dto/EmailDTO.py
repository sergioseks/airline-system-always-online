from injector import inject


class EmailDTO:
    def __init__(self):
        self.error_code = ""
        self.error_message = ""
        self.http_code = ""
        self.data = None
        self.email_dto = dict()

    @inject
    def get_error_code(self):
        return self.error_code

    @inject
    def set_error_code(self, error_code):
        self.error_code = error_code

    @inject
    def get_error_message(self):
        return self.error_message

    @inject
    def set_error_message(self, error_message):
        self.error_message = error_message

    @inject
    def get_http_code(self):
        return self.http_code

    @inject
    def set_http_code(self, http_code):
        self.http_code = http_code

    @inject
    def get_data(self):
        return self.data

    @inject
    def set_data(self, data):
        self.data = data

    @inject
    def email_dto_to_dict(self):
        self.email_dto = {
            "errorCode": self.error_code,
            "errorMessage": self.error_message,
            "httpCode": self.http_code,
            "data": self.data
        }

        return self.email_dto
