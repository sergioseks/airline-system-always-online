class Log(object):
    def __init__(self, request):
        self._id = request["_id"]
        self.level = request["level"]
        self.description = request["description"]
        self.date = request["date"]

        self.log_dict = {
            "level": self.level,
            "description": self.description,
            "date": self.date
        }

    def get_id(self):
        return self._id

    def set_id(self, id: str):
        self._id = id

    def get_level(self):
        return self.level

    def set_level(self, level: str):
        self.level = level

    def get_description(self):
        return self.description

    def set_description(self, description: str):
        self.description = description

    def get_date(self):
        return self.date

    def set_date(self, date: str):
        self.date = date

    def get_log_dict(self):
        return self.log_dict

    def set_log_dict(self, log_dict):
        self.log_dict = log_dict
