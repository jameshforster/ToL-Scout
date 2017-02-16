package handlers

import exceptions.NotStartedException
import helpers.TestSpec
import org.mockito.Mockito._
import org.spongepowered.api.{Game, Server}

import scala.util.{Failure, Success}

class ServerRequestHandlerSpec extends TestSpec {

  def setupMockGame(isStarted: Boolean): Game = {

    val mockGame = mock[Game]

    when(mockGame.isServerAvailable)
      .thenReturn(isStarted)

    mockGame
  }

  "Calling .handleRequest" should {

    "return a NotStartedException when a server is not running" in {
      def testFunction() = setupMockGame(false).getServer
      val handler = new ServerRequestHandler(setupMockGame(false))
      lazy val result = handler.handleRequest[Server] {
        testFunction()
      }

      result shouldBe Failure(NotStartedException())
    }

    "return a valid result when a server is running" when {
      val handler = new ServerRequestHandler(setupMockGame(true))

      "provided with a parameterless function" in {
        def testFunction(): Boolean = true
        lazy val result = handler.handleRequest[Boolean] {
          testFunction()
        }

        result shouldBe Success(true)
      }

      "provided with a single variable function" in {
        def testFunction(input: Int): Boolean = input > 0
        lazy val result = handler.handleRequest[Boolean] {
          testFunction(3)
        }

        result shouldBe Success(true)
      }

      "provided with a multiple variable function" in {
        def testFunction(value1: Int, value2: Int): Boolean = value1.equals(value2)
        lazy val result = handler.handleRequest[Boolean] {
          testFunction(2, 2)
        }

        result shouldBe Success(true)
      }
    }
  }
}
