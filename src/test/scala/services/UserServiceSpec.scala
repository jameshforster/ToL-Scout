package services

import java.util

import connectors.GameConnector
import exceptions.ServerNotRespondingException
import handlers.ServerRequestHandler
import helpers.TestSpec
import models.UserModel
import org.mockito.Mockito._
import org.slf4j.Logger
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.{Game, Server}

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

class UserServiceSpec extends TestSpec {

  def setupMockService(serverResponse: Try[Server]): UserService = {
    val mockGame = mock[Game]
    val mockLogger = mock[Logger]
    val mockHandler = new ServerRequestHandler(mockGame, mockLogger)
    val mockConnector = mock[GameConnector]

    when(mockGame.isServerAvailable)
      .thenReturn(true)

    when(mockConnector.fetchServer())
      .thenReturn(serverResponse)

    new UserService(mockConnector, mockHandler)
  }

  def setupMockServer(playerList: util.Collection[Player]): Server = {
    val mockServer = mock[Server]

    when(mockServer.getOnlinePlayers)
      .thenReturn(playerList)

    mockServer
  }

  def setupMockPlayer(name: String): Player = {
    val mockPlayer = mock[Player]

    when(mockPlayer.getName)
      .thenReturn(name)

    mockPlayer
  }

  "Calling .fetchOnlinePlayers" should {

    "return a list of players from the server" in {
      val playerList = List(mock[Player], mock[Player])
      val server = setupMockServer(playerList.asJava)
      val service = setupMockService(Success(server))
      val result = service.fetchOnlinePlayers()

      result shouldBe Success(playerList)
    }

    "return a failure when an error is thrown" in {
      val service = setupMockService(Failure(ServerNotRespondingException()))
      val result = service.fetchOnlinePlayers()

      result shouldBe Failure(ServerNotRespondingException())
    }
  }

  "Calling .fetchOnlineUsers" should {

    "return a list of users from the server" in {
      val mockPlayers = List(setupMockPlayer("name1"), setupMockPlayer("name2"))
      val server = setupMockServer(mockPlayers.asJava)
      val service = setupMockService(Success(server))
      val result = service.fetchOnlineUsers()

      result shouldBe Success(List(UserModel("name1"), UserModel("name2")))
    }

    "return a failure when an error is thrown" in {
      val service = setupMockService(Failure(ServerNotRespondingException()))
      val result = service.fetchOnlineUsers()

      result shouldBe Failure(ServerNotRespondingException())
    }
  }
}
