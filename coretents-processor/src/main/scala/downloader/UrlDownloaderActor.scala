package downloader

import java.net.URL

import akka.actor.{Props, _}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.{ActorMaterializer, ActorMaterializerSettings}
import akka.util.ByteString

import scala.concurrent.Future
import scala.io.Source
import parser._
import data._

import scala.util.{Failure, Success}

class UrlDownloaderActor() extends DownloaderActor {

    import context.dispatcher

    val xmlParser = context.system.actorOf(Props(new RssParserActor()))

    final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))
    val http = Http(context.system)

    override def download(source: SourceData) = {
        http.singleRequest(HttpRequest(uri = source.url)).
            flatMap(response => response.entity.dataBytes.runFold(ByteString(""))(_ ++ _)).
            map(entity => entity.utf8String).map(content => new RawData(Option(source), content))

    }

}