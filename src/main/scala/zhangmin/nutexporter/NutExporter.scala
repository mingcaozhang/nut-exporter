package zhangmin.nutexporter

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{complete, get, path}
import com.typesafe.scalalogging.LazyLogging
import zhangmin.nutexporter.config.{Settings, SettingsLoader}

import pureconfig.generic.auto._
import scala.concurrent.ExecutionContext

object NutExporter extends App with LazyLogging {
  implicit val actorSystem: ActorSystem = ActorSystem("nut-exporter")
  implicit val ec: ExecutionContext = actorSystem.dispatcher

  val settings = SettingsLoader.load[Settings]("nut-exporter")
  val command = s"upsc ${settings.upsName}@${settings.upsdHost}:${settings.upsdPort}"

  val route = path("metrics") {
    get { complete(NutWrapper.getPrometheusNutOutput(command)) }
  }

  Http().newServerAt(settings.host, settings.port).bindFlow(route).onComplete { _ =>
    logger.info(s"Server listening for requests at ${settings.host}:${settings.port}")
    logger.info(s"Command run every scrape: $command")
  }
}
