class Email(object):
    def __init__(self, request):
        self._id = request["_id"]
        self.traveler_email = request["traveler_email"]
        self.date = request["date"]

        self.email_dict = {
            "traveler_email": self.traveler_email,
            "date": self.date
        }

    def get_id(self):
        return self._id

    def set_id(self, id: str):
        self._id = id

    def get_traveler_email(self):
        return self.traveler_email

    def set_traveler_email(self, traveler_email: str):
        self.traveler_email = traveler_email

    def get_date(self):
        return self.date

    def set_date(self, date: str):
        self.date = date

    def get_email_dict(self):
        return self.email_dict

    def set_email_dict(self, email_dict):
        self.email_dict = email_dict
