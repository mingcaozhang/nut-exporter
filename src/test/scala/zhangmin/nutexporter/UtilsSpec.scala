package zhangmin.nutexporter

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UtilsSpec extends AnyFlatSpec with Matchers {

  "Utils.listToPair" should "convert a list of exactly two elements to a pair" in {
    Utils.listToPair(List(1, 2)) shouldBe Some((1, 2))
  }

  it should "convert a list with 3 or more elements to None" in {
    Utils.listToPair(List(1, 2, 3)) shouldBe None
    Utils.listToPair(List(1, 2, 3, 4)) shouldBe None
  }

  it should "convert a list with less than 2 elements to None" in {
    Utils.listToPair(List(1)) shouldBe None
    Utils.listToPair(List.empty) shouldBe None
  }
}
