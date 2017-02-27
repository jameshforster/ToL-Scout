package controllers

import config.AppConfig
import handlers.{StdHttpExceptionHandler, UserListRequestHandler}
import helpers.TestSpec
import org.slf4j.Logger
import services.UserService

class ServerControllerSpec extends TestSpec {

  "Server controller" should {
    lazy val mockConfig = new AppConfig {
      override val hideMods = false
      override val listenerPort = 9700
    }
    lazy val controller = new ServerController(new StdHttpExceptionHandler(mock[Logger]), new UserListRequestHandler(mock[UserService]), mockConfig)

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
