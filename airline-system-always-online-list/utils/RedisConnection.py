import redis
from datetime import timedelta
from injector import inject
import os


class RedisConnection(object):
    def __init__(self):
        self.redis = None
        self.expiration = 0
        self.host = '%s' % (os.environ['REDIS_SERVICE_HOST'])
        self.port = '%s' % (os.environ['REDIS_SERVICE_PORT'])
        self.init_redis()
        # self.password = '%s' % (os.environ['REDIS_SERVICE_PASSWORD'])

    def init_redis(self):
        self.expiration = 5
        self.redis = redis.Redis(host=self.host, port=int(self.port), db=0, password=None, socket_timeout=None)
        # self.redis = redis.Redis(host='localhost', port=int(6379), db=0, password=None, socket_timeout=None)

    @inject
    def get_value(self, key: str):
        return self.redis.get(key)

    @inject
    def exists_value(self, key: str):
        return self.redis.exists(key)

    @inject
    def set_value(self, key: str, value_to_cache):
        self.redis.setex(key, timedelta(minutes=self.expiration), value_to_cache)
