package zhangmin.nutexporter

import scala.sys.process._

object NutWrapper {

  /**
    * Get the nut output in the prometheus metrics format as a string.
    * Runs the argument string as a process.
    *
    * @param upscCommand upsc command string to be run
    * @return prometheus metrics formatted nut output
    */
  def getPrometheusNutOutput(upscCommand: String): String = {
    val rawOutput = upscCommand !!
    val outputMapping = parseRawOutput(rawOutput)
    formatOutput(outputMapping)
  }

  /**
    * Convert `upsc` output into a `Map[String,String`
    * @param rawOutput upsc output
    * @return mapping of upsc key-value pairs
    */
  def parseRawOutput(rawOutput: String): Map[String, String] =
    rawOutput
      .split('\n')
      .flatMap(line => Utils.listToPair(line.split(":").toList.map(_.trim)))
      .toMap

  /**
    * Collect power draw and remaining battery metrics and format them as required for Prometheus metrics scraping.
    * @param outputMapping upsc mapping
    * @return power draw and battery life metrics in Prometheus format
    */
  def formatOutput(outputMapping: Map[String, String]): String = {
    val batteryRuntime = outputMapping
      .get("battery.runtime")
      .map(metricWithMetadata(_, "battery_runtime", "Remaining Battery Runtime in Seconds", "gauge"))

    val batteryCapacity = outputMapping
      .get("battery.charge")
      .map(metricWithMetadata(_, "battery_capacity", "Remaining Battery Percentage", "gauge"))

    val powerDraw = for {
      loadPercentage <- outputMapping.get("ups.load").map(_.toDouble)
      availablePower <- outputMapping.get("ups.realpower.nominal").map(_.toDouble)
    } yield {
      val load = (loadPercentage / 100) * availablePower
      metricWithMetadata(load.toString, "power_draw", "Current Power Draw in KW", "gauge")
    }
    List(batteryCapacity, batteryRuntime, powerDraw).flatten.mkString("\n")
  }

  /**
    * Generate the metric string with # HELP and # TYPE metadata.
    * @param metricValue metric value
    * @param metricName metric name
    * @param metricDescription metadata description
    * @param metricType prometheus metric type
    * @return correctly formatted metric string with metadata
    */
  def metricWithMetadata(
    metricValue: String,
    metricName: String,
    metricDescription: String,
    metricType: String
  ): String = s"# HELP $metricName $metricDescription\n# TYPE $metricName $metricType\n$metricName $metricValue"
}
