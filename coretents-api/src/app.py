from flask import Flask
from bson.json_util import dumps
from coretents.db.mongo import CoretentsSchema
from coretents.config.configparser import CoretentsConfig

app = Flask(__name__)
config = CoretentsConfig()['API']
schema = CoretentsSchema()

@app.route('/')
def api_information():
    return 'Coretents API is up'

@app.route('/resources')
def resources():
    return dumps(schema.resources())

if __name__ == '__main__':
    app.run(debug=config.getboolean('debug'),host=config.get('host'),port=config.getint('port'))