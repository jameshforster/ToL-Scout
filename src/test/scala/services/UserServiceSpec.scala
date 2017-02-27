package services

import java.util

import config.ApplicationConfig
import connectors.GameConnector
import exceptions.ServerNotRespondingException
import handlers.ServerRequestHandler
import helpers.TestSpec
import models.UserModel
import org.mockito.ArgumentMatchers
import org.mockito.Mockito._
import org.slf4j.Logger
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.{Game, Server}

import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

class UserServiceSpec extends TestSpec {

  def setupMockService(serverResponse: Try[Server], config: ApplicationConfig): UserService = {
    val mockGame = mock[Game]
    val mockLogger = mock[Logger]
    val mockHandler = new ServerRequestHandler(mockGame, mockLogger)
    val mockConnector = mock[GameConnector]

    when(mockGame.isServerAvailable)
      .thenReturn(true)

    when(mockConnector.fetchServer())
      .thenReturn(serverResponse)

    new UserService(mockConnector, mockHandler, config)
  }

  def setupMockServer(playerList: util.Collection[Player]): Server = {
    val mockServer = mock[Server]

    when(mockServer.getOnlinePlayers)
      .thenReturn(playerList)

    mockServer
  }

  def setupMockPlayer(name: String, isMod: Boolean = false): Player = {
    val mockPlayer = mock[Player]

    when(mockPlayer.getName)
      .thenReturn(name)

    when(mockPlayer.hasPermission(ArgumentMatchers.any()))
      .thenReturn(isMod)

    mockPlayer
  }

  def setupMockConfig(isFiltering: Boolean): ApplicationConfig = {
    val mockConfig = mock[ApplicationConfig]

    when(mockConfig.hideMods)
      .thenReturn(isFiltering)

    mockConfig
  }

  "Calling .fetchOnlinePlayers" should {
    val config = setupMockConfig(false)

    "return a list of players from the server" in {
      val playerList = List(mock[Player], mock[Player])
      val server = setupMockServer(playerList.asJava)
      val service = setupMockService(Success(server), config)
      val result = service.fetchOnlinePlayers()

      result shouldBe Success(playerList)
    }

    "return a failure when an error is thrown" in {
      val service = setupMockService(Failure(ServerNotRespondingException()), config)
      val result = service.fetchOnlinePlayers()

      result shouldBe Failure(ServerNotRespondingException())
    }
  }

  "Calling .fetchOnlineUsers" should {

    "return a list of users from the server" in {
      val config = setupMockConfig(false)
      val mockPlayers = List(setupMockPlayer("name1"), setupMockPlayer("name2", isMod = true))
      val server = setupMockServer(mockPlayers.asJava)
      val service = setupMockService(Success(server), config)
      val result = service.fetchOnlineUsers()

      result shouldBe Success(List(UserModel("name1"), UserModel("name2")))
    }

    "return a filtered list if filtering is enabled" in {
      val config = setupMockConfig(true)
      val mockPlayers = List(setupMockPlayer("name1"), setupMockPlayer("name2", isMod = true))
      val server = setupMockServer(mockPlayers.asJava)
      val service = setupMockService(Success(server), config)
      val result = service.fetchOnlineUsers()

      result shouldBe Success(List(UserModel("name1")))
    }

    "return a failure when an error is thrown" in {
      val config = setupMockConfig(false)
      val service = setupMockService(Failure(ServerNotRespondingException()), config)
      val result = service.fetchOnlineUsers()

      result shouldBe Failure(ServerNotRespondingException())
    }
  }
}
