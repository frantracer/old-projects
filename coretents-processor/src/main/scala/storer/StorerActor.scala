package storer

import akka.actor.{Actor, ActorLogging}
import data.{ParsedData, TaskOutcome}

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait StorerActor extends Actor
    with ActorLogging {

    import context.dispatcher

    override def receive = {
        case data:ParsedData => {
            val my_sender = sender()
            store(data) onComplete {
                case Success(outcome) =>
                    my_sender ! outcome
                case Failure(e) =>
                    e.printStackTrace()
                    my_sender ! new TaskOutcome(1)
            }
        }
        case _ =>
            println("Something in Storer")
    }

    def store(data: ParsedData): Future[TaskOutcome]
}
