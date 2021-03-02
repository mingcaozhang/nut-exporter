import com.typesafe.scalalogging.LazyLogging
import config.{Settings, SettingsLoader}
import pureconfig.generic.auto._

object NutExporter extends App with LazyLogging {

  val settings = SettingsLoader.load[Settings]("nut-exporter")
  logger.info(s"Port: ${settings.port}")

}
