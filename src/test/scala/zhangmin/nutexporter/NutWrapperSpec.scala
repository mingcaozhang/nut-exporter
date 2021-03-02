package zhangmin.nutexporter

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NutWrapperSpec extends AnyFlatSpec with Matchers {
  "NutWrapper.parseRawOutput" should "convert valid upsc output to a Map" in {
    val validNutOutput =
      """
        |a:1
        |b :2
        |""".stripMargin

    NutWrapper.parseRawOutput(validNutOutput) shouldBe Map("a" -> "1", "b" -> "2")
  }

  it should "ignore invalid lines in the output" in {
    val invalidNutOutput =
      """
        |a.1
        |b2
        |c 3
        |""".stripMargin

    val validNutOutputWithInvalidLines =
      """
        |a:1
        |b :2
        |c 3
        |""".stripMargin

    NutWrapper.parseRawOutput(invalidNutOutput) shouldBe Map.empty
    NutWrapper.parseRawOutput(validNutOutputWithInvalidLines) shouldBe Map("a" -> "1", "b" -> "2")
  }

  "NutWrapper.formatOutput" should "return a valid prometheus metric when all required keys are present" in {
    NutWrapper
      .formatOutput(
        Map(
          "battery.runtime" -> "2500",
          "battery.charge" -> "100",
          "ups.load" -> "10",
          "ups.realpower.nominal" -> "100"
        )
      ) shouldBe
      """# HELP battery_capacity Remaining Battery Percentage
        |# TYPE battery_capacity gauge
        |battery_capacity 100
        |# HELP battery_runtime Remaining Battery Runtime in Seconds
        |# TYPE battery_runtime gauge
        |battery_runtime 2500
        |# HELP power_draw Current Power Draw in KW
        |# TYPE power_draw gauge
        |power_draw 10.0""".stripMargin
  }

  it should "return a valid prometheus metric even if some required keys are missing" in {
    NutWrapper
      .formatOutput(
        Map(
          "battery.charge" -> "100",
          "ups.load" -> "10",
          "ups.realpower.nominal" -> "100"
        )
      ) shouldBe
      """# HELP battery_capacity Remaining Battery Percentage
        |# TYPE battery_capacity gauge
        |battery_capacity 100
        |# HELP power_draw Current Power Draw in KW
        |# TYPE power_draw gauge
        |power_draw 10.0""".stripMargin

    NutWrapper
      .formatOutput(
        Map(
          "battery.runtime" -> "2500",
          "ups.load" -> "10",
          "ups.realpower.nominal" -> "100"
        )
      ) shouldBe
      """# HELP battery_runtime Remaining Battery Runtime in Seconds
        |# TYPE battery_runtime gauge
        |battery_runtime 2500
        |# HELP power_draw Current Power Draw in KW
        |# TYPE power_draw gauge
        |power_draw 10.0""".stripMargin

    NutWrapper
      .formatOutput(
        Map(
          "battery.runtime" -> "2500",
          "battery.charge" -> "100",
          "ups.realpower.nominal" -> "100"
        )
      ) shouldBe
      """# HELP battery_capacity Remaining Battery Percentage
        |# TYPE battery_capacity gauge
        |battery_capacity 100
        |# HELP battery_runtime Remaining Battery Runtime in Seconds
        |# TYPE battery_runtime gauge
        |battery_runtime 2500""".stripMargin
  }

}
