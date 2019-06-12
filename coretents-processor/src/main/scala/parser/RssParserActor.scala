package parser

import akka.actor.{Props, _}
import data.{Item, ParsedData, RawData, Resource}

import scala.xml.XML
import storer.MongodbStorerActor

import scala.concurrent.Future

class RssParserActor() extends ParserActor {

    import context.dispatcher

    val dataStorer = context.system.actorOf(Props(new MongodbStorerActor()))

    override def parse(raw_data: RawData) = {
        Future {
            val xml = XML.loadString(raw_data.data)

            // Get information from the resource
            val updated = (xml \ "updated").text
            val link = (xml \ "link").filter(i => (i \ "@rel").text == "self").head.attribute("href").getOrElse("").toString()
            val icon = (xml \ "icon").text
            val resource : Resource = new Resource(
                Map(
                    "updated" -> updated,
                    "link" -> link,
                    "icon" -> icon
                ),
                "link"
            )

            // Get information from the entries
            val entries = (xml \\ "entry")
            val items : List[Item] =
                entries.map(
                    entry =>
                    {
                        val title = ((entry \ "title") text)
                        val author = ((entry \ "author" \ "name") text)
                        val link = (entry \ "link").head.attribute("href").getOrElse("").toString()
                        val updated = ((entry \ "updated") text)

                        val data = Map(
                            "title" -> title,
                            "author" -> author,
                            "link" -> link,
                            "updated" -> updated
                        )

                        new Item(data, "title")
                    }).toList

            new ParsedData(resource, items)
        }
    }
}
