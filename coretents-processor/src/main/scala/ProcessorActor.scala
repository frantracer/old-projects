import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import downloader._
import parser._
import storer._
import data._

class ProcessorActor (val parent : ActorRef) extends Actor
    with ActorLogging {

    import akka.pattern.pipe
    import context.dispatcher

    val downloader = context.system.actorOf(Props(new UrlDownloaderActor))
    val parser = context.system.actorOf(Props(new RssParserActor))
    val storer = context.system.actorOf(Props(new MongodbStorerActor))

    override def receive = {
        case source:SourceData =>
            println("Download")
            downloader ! source;
        case raw_data:RawData =>
            println("Parse")
            parser ! raw_data;
        case parsed_data:ParsedData =>
            println("Store")
            storer ! parsed_data;
        case outcome:TaskOutcome =>
            println("Task finished with " + outcome.status)
            parent ! outcome
        case e =>
            println("Something in Processor" + e.toString())
    }
}
