package handlers

import java.net.SocketTimeoutException

import helpers.TestSpec
import org.apache.http.ConnectionClosedException
import org.slf4j.Logger

class StdHttpExceptionHandlerSpec extends TestSpec {

  val handler = new StdHttpExceptionHandler(mock[Logger])

  "The HttpExceptionHandler" should {

    "handle a SocketTimeoutException" in {
      val exception = new SocketTimeoutException()
      val result = handler.log(exception)

      result shouldBe {}
    }

    "handle a ConnectionClosedException" in {
      val exception = new ConnectionClosedException("")
      val result = handler.log(exception)

      result shouldBe {}
    }

    "handle a generic Exception" in {
      val exception = new Exception("test message")
      val result = handler.log(exception)

      result shouldBe {}
    }
  }
}
