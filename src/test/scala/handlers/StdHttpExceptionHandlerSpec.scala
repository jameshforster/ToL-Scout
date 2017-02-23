package handlers

import java.net.SocketTimeoutException

import helpers.TestSpec
import org.apache.http.ConnectionClosedException
import org.slf4j.Logger

class StdHttpExceptionHandlerSpec extends TestSpec {

  "An http exception" which {
    val handler = new StdHttpExceptionHandler(mock[Logger])

    "has the type SocketTimeoutException" should {

      "be handled by the HttpExceptionHandler correctly" in {
        val exception = new SocketTimeoutException()
        val result = handler.log(exception)

        result shouldBe {}
      }
    }

    "has the type ConnectionClosedException" should {

      "be handled by the HttpExceptionHandler correctly" in {
        val exception = new ConnectionClosedException("")
        val result = handler.log(exception)

        result shouldBe {}
      }
    }

    "is a generic Exception" should {

      "be handled by the HttpExceptionHandler correctly" in {
        val exception = new Exception("test message")
        val result = handler.log(exception)

        result shouldBe {}
      }
    }
  }
}
