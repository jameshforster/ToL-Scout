package services

import com.google.inject.{Inject, Singleton}
import connectors.GameConnector
import handlers.ServerRequestHandler
import models.UserModel
import org.spongepowered.api.entity.living.player.Player

import scala.collection.JavaConverters._
import scala.util.{Success, Try}

@Singleton
class UserService @Inject()(gameConnector: GameConnector, serverRequestHandler: ServerRequestHandler) {

  def fetchOnlinePlayers(): Try[List[Player]] = {
    serverRequestHandler.handleRequest {
      gameConnector.fetchServer().flatMap { server =>
        Success(server.getOnlinePlayers.asScala.toList)
      }
    }
  }

  def fetchOnlineUsers(): Try[List[UserModel]] = {
    fetchOnlinePlayers().flatMap { players =>
      Success(players)
    }
  }
}
