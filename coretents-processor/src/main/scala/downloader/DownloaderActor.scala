package downloader

import scala.util.{Failure, Success}
import akka.actor.{Actor, ActorLogging}
import data.{RawData, SourceData}

import scala.concurrent.Future

trait DownloaderActor extends Actor
    with ActorLogging {

    import context.dispatcher

    override def receive = {
        case source:SourceData => {
            val my_sender = sender()
            download(source) onComplete {
                case Success(data: RawData) => my_sender ! data
                case Failure(e) => e.printStackTrace()
            }
        }
        case _ =>
            println("Something in Downloader")
    }

    def download(source: SourceData): Future[RawData]
}
