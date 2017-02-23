package controllers

import handlers.{StdHttpExceptionHandler, UserListRequestHandler}
import helpers.TestSpec
import org.slf4j.Logger
import services.UserService

class ServerControllerSpec extends TestSpec {

  "Server controller" should {
    lazy val controller = new ServerController(new StdHttpExceptionHandler(mock[Logger]), new UserListRequestHandler(mock[UserService]))

    "start the server successfully" in {
      lazy val result = controller.startServer()

      result shouldBe {}
    }

    "stop the server successfully" in {
      lazy val result = {
        controller.startServer()
        controller.stopServer()
      }

      result shouldBe {}
    }
  }
}
