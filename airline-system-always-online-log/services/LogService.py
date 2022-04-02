from builtins import object

from models.Log import Log
from utils.Connection import Connection as connection
from injector import Injector, inject
from bson.objectid import ObjectId


class LogService(object):

    def __init__(self):
        self.db = Injector().get(connection).get_db()["log"]

    @inject
    def get_all(self) -> list:
        customers = self.db.find()
        customers_list = list()

        for c in customers:
            c["_id"] = str(c["_id"])
            customers_list.append(c)

        return customers_list

    @inject
    def save(self, log: Log):
        c = self.db.insert_one(log.get_log_dict())

        log.get_log_dict()["_id"] = str(c.inserted_id)
        return log.get_log_dict()

    @inject
    def update(self, log: Log):
        cg = self.get_by_id(log.get_id())

        if cg is not None:
            c = self.db.update_one({"_id": cg["_id"]}, {"$set": log.get_log_dict()})

            if c.modified_count >= 1:
                log.get_log_dict()["_id"] = log.get_id()
                return log.get_log_dict()

    @inject
    def get_by_id(self, id: str):
        c = self.db.find_one({"_id": ObjectId(id)})
        return c

    @inject
    def remove_by_id(self, id: str):
        self.db.delete_one({"_id": ObjectId(id)})

    @inject
    def remove_all(self):
        self.db.delete_many({})
