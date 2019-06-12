import akka.actor._

import scala.concurrent.duration._
import akka.routing.RoundRobinPool
import data.SourceData

object Main extends App {

    val system = ActorSystem("Coretents")

    val factoryActor = system.actorOf(RoundRobinPool(5).props(Props[ProcessorActorFactory]), "processorFactory")

    import system.dispatcher

    system.scheduler.schedule(0 seconds, 10 seconds) {
        factoryActor ! new SourceData("https://www.reddit.com/r/programming/.rss")
    }
}