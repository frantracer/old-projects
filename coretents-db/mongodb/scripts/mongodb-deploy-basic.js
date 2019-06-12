connection = new Mongo();
db = connection.getDB("coretents-db");

db.createCollection("resources");
db.createCollection("entries");
