from pymongo import MongoClient
from coretents.config.configparser import CoretentsConfig

class MongoConnection():
    __connection = None

    def __new__(cls):
        if MongoConnection.__connection is None:
            config = CoretentsConfig()['MONGODB']
            MongoConnection.__connection = MongoClient(config.get('host'), config.getint('port'))[config.get('dbname')]
        return MongoConnection.__connection


class CoretentsSchema():
    def __init__(self):
        self.connection = MongoConnection()

    def resources(self, query = None, projection = None, with_entries = 1):
        resources = []
        for resource in self.connection.resources.find(query, projection):
            if with_entries:
                resource["entries"] = self.entries({"resource_id" : resource["_id"]}, {"resource_id" : 0})
            resources.append(resource)
        return resources

    def entries(self, query = None, projection = None):
        entries = []
        for entry in self.connection.entries.find(query, projection):
            entries.append(entry)
        return entries

