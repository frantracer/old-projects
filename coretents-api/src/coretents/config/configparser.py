import configparser
from pathlib import Path

CONFIG_PATH = "/etc/coretents-api.ini"

class CoretentsConfig():
    __parser = None

    def __new__(cls):
        if CoretentsConfig.__parser is None:
            config_file = Path(CONFIG_PATH)
            if config_file.is_file():
                CoretentsConfig.__parser = configparser.ConfigParser()
                CoretentsConfig.__parser.read(CONFIG_PATH)
            else:
                raise Exception("Configuration file %s does not exist" % CONFIG_PATH)
        return CoretentsConfig.__parser

