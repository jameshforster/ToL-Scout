package example

import assets.TestSpec

class HelloSpec extends TestSpec {

  "The Hello object" should {
    "say hello" in {
      val result = Hello.greeting

      result shouldBe "hello"
    }
  }
}
