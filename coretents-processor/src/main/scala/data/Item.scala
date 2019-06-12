package data

import reactivemongo.bson._

/**
  * Created by frantracer on 6/21/17.
  */
class Item (data : Map[String, Any], primary_key : String) {

    def writeObject(obj: Map[String,Any]): BSONDocument = BSONDocument(obj.map(writePair))

    def writePair(p: (String, Any)): (String, BSONValue) = (p._1, p._2 match     {
        case value: String  => BSONString(value)
        case value: Double  => BSONDouble(value)
        case value: Int     => BSONInteger(value)
        case value: Boolean => BSONBoolean(value)
        case value: Long    => BSONLong(value)
        case other => BSONNull
    })

    def toBSONDocument():BSONDocument = {
        writeObject(data)
    }

    def primaryKey():BSONDocument = {
        val bson = BSONDocument(primary_key -> getValue(primary_key).toString())
        println(getValue(primary_key).toString())
        bson
    }

    def getValue(field : String):Any = {
        data.getOrElse(field, "")
    }

}
