package connectors

import java.util.logging.Logger

import exceptions.ServerNotRespondingException
import helpers.TestSpec
import org.mockito.Mockito._
import org.spongepowered.api.{Game, Server}

import scala.util.{Failure, Success}

class GameConnectorSpec extends TestSpec {

  val mockServer = mock[Server]

  def setupMockConnector(returnServer: Boolean): GameConnector = {

    val mockLogger = mock[Logger]
    val mockGame = mock[Game]

    if (returnServer) {
      when(mockGame.getServer)
        .thenReturn(mockServer)
    } else when(mockGame.getServer)
      .thenThrow(new IllegalStateException)

    new GameConnector(mockGame, mockLogger)
  }

  "Calling .fetchServer" should {

    "return a Success containing the server" in {
      val connector = setupMockConnector(returnServer = true)
      val result = connector.fetchServer()

      result shouldBe Success(mockServer)
    }

    "handle an exception with the correct response" in {
      val connector = setupMockConnector(returnServer = false)
      val result = connector.fetchServer()

      result shouldBe Failure(ServerNotRespondingException())
    }
  }
}
