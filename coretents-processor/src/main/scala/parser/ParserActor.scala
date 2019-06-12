package parser

import akka.actor.{Actor, ActorLogging}
import data.{ParsedData, RawData}

import scala.concurrent.Future
import scala.util.{Failure, Success}

trait ParserActor extends Actor
    with ActorLogging {

    import context.dispatcher

    override def receive = {
        case raw_data:RawData => {
            val my_sender = sender()
            parse(raw_data) onComplete {
                case Success(parsed_data) => {
                    my_sender ! parsed_data
                }
                case Failure(e) => e.printStackTrace()
            }
        }
        case _ =>
            println("Something in Parser")
    }

    def parse(data: RawData): Future[ParsedData]

}
