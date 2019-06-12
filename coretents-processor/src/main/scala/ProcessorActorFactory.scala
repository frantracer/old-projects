import akka.actor.{Actor, Props}
import data.{SourceData, TaskOutcome}

/**
  * Created by frantracer on 6/21/17.
  */
class ProcessorActorFactory extends Actor {
    override def receive: Receive = {
        case source : SourceData => {
            val processor = context.system.actorOf(Props(new ProcessorActor(self)))
            processor ! source
        }
        case outcome : TaskOutcome => {
            println("Processor Actor finished with " + outcome.status)
        }
        case _ => {
            println("Something in ProcessorFactory")
        }
    }
}
