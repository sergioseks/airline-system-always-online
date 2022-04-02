from pymongo import MongoClient
from injector import inject
import os
from pprint import pprint


class Connection(object):
    def __init__(self):
        self.client = None
        self.mongodb_host = '%s' % (os.environ['MONGODB_SERVICE_HOST'])
        self.mongodb_port = '%s' % (os.environ['MONGODB_SERVICE_PORT'])
        self.mongodb_database = '%s' % (os.environ['MONGODB_DATABASE'])
        self.init_mongodb()

    def init_mongodb(self):
        self.client = MongoClient(self.mongodb_host, int(self.mongodb_port))

    @inject
    def get_db(self):
        db = self.client[self.mongodb_database]
        return db
