package storer

import data.{Item, ParsedData, Resource, TaskOutcome}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.MongoDriver
import reactivemongo.bson.BSONDocument

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object MongoDatabase {

    def db_collection_curried (driver : MongoDriver) (address: String) (databaseName: String) (collectionName: String) : Future[BSONCollection] =
        driver.connection(List(address)).database(databaseName).map(_.collection(collectionName))

    val db_collection = db_collection_curried (new MongoDriver()) ("localhost:27017") ("coretents-db") (_)

    val resources = db_collection ("resources")
    val entries = db_collection ("entries")

    def updateResource(document: BSONDocument, primary_key: BSONDocument) : Future[Option[BSONDocument]] = {
        resources.flatMap (_.findAndUpdate(
            primary_key,
            document,
            upsert = true,
            fetchNewObject = true
        ).map(_.result))
    }

    def updateEntry(document: BSONDocument, primary_key: BSONDocument, resource: Option[BSONDocument]) : Future[Option[BSONDocument]] = {
        entries.flatMap(entry => {
            val resource_id_document : BSONDocument = BSONDocument("_resource_id" -> resource.getOrElse(BSONDocument()).get("_id"))
            entry.findAndUpdate(
                primary_key,
                document ++ resource_id_document,
                upsert = true,
                fetchNewObject = true
            ).map(_.result)
        })
    }

}

class MongodbStorerActor() extends StorerActor {

    override def store(data: ParsedData) = {
        val resource_updated: Future[Option[BSONDocument]] = MongoDatabase.updateResource(data.resource.toBSONDocument(), data.resource.primaryKey())
        val future_list = resource_updated.flatMap(resource => Future.sequence(data.entries
            .map(entry => MongoDatabase.updateEntry(entry.toBSONDocument(), entry.primaryKey(), resource))))

        future_list.map(_ => new TaskOutcome(0))
    }

}
