from injector import Injector, inject
from utils.RedisConnection import RedisConnection as redis_connection


class RedisService(object):
    def __init__(self):
        self.redis_connector = Injector().get(redis_connection)

    @inject
    def get_value(self, key: str):
        return self.redis_connector.get_value(key)

    @inject
    def exists_value(self, key: str):
        return self.redis_connector.exists_value(key)

    @inject
    def set_value(self, key: str, value: str):

        try:
            self.redis_connector.set_value(key, value)
        except Exception as e:
            print(e)
