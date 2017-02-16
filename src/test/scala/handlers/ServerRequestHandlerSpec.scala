package handlers

import exceptions.ServerNotStartedException
import helpers.TestSpec
import org.mockito.Mockito._
import org.slf4j.Logger
import org.spongepowered.api.{Game, Server}

import scala.util.{Failure, Success, Try}

class ServerRequestHandlerSpec extends TestSpec {

  val mockLogger = mock[Logger]

  def setupMockGame(isStarted: Boolean): Game = {

    val mockGame = mock[Game]

    when(mockGame.isServerAvailable)
      .thenReturn(isStarted)

    mockGame
  }

  "Calling .handleRequest" should {

    "return a NotStartedException when a server is not running" in {
      def testFunction() = Try(setupMockGame(false).getServer)

      val handler = new ServerRequestHandler(setupMockGame(false), mockLogger)
      lazy val result = handler.handleRequest[Server] {
        testFunction()
      }

      result shouldBe Failure(ServerNotStartedException())
    }

    "return a valid result when a server is running" when {
      val handler = new ServerRequestHandler(setupMockGame(true), mockLogger)

      "provided with a parameterless function" in {
        def testFunction() = Try(true)

        lazy val result = handler.handleRequest[Boolean] {
          testFunction()
        }

        result shouldBe Success(true)
      }

      "provided with a single variable function" in {
        def testFunction(input: Int) = Try(input > 0)

        lazy val result = handler.handleRequest[Boolean] {
          testFunction(3)
        }

        result shouldBe Success(true)
      }

      "provided with a multiple variable function" in {
        def testFunction(value1: Int, value2: Int) = Try(value1.equals(value2))

        lazy val result = handler.handleRequest[Boolean] {
          testFunction(2, 2)
        }

        result shouldBe Success(true)
      }
    }
  }
}
