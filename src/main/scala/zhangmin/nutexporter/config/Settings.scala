package zhangmin.nutexporter.config

/**
  * Settings for NutExporter.
  *
  * @param host nut exporter host
  * @param port nut exporter port
  * @param upsName name of the ups in the nut server's /etc/ups.conf
  * @param upsdHost nut server host
  * @param upsdPort nut server port
  */
final case class Settings(host: String, port: Int, upsName: String, upsdHost: String, upsdPort: Int)
