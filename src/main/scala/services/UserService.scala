package services

import com.google.inject.{Inject, Singleton}
import config.ApplicationConfig
import connectors.GameConnector
import handlers.ServerRequestHandler
import models.UserModel
import org.spongepowered.api.entity.living.player.Player
import keys.PermissionKeys._

import scala.collection.JavaConverters._
import scala.util.{Success, Try}

@Singleton
class UserService @Inject()(gameConnector: GameConnector, serverRequestHandler: ServerRequestHandler, applicationConfig: ApplicationConfig) {

  def fetchOnlinePlayers(): Try[List[Player]] = {
    serverRequestHandler.handleRequest {
      gameConnector.fetchServer().flatMap { server =>
        Success(server.getOnlinePlayers.asScala.toList)
      }
    }
  }

  def fetchOnlineUsers(): Try[List[UserModel]] = {

    def filterUsersByPermission(players: List[Player]): Try[List[UserModel]] = {
      Success(players.filterNot(_.hasPermission(hiddenFromScout)))
    }

    fetchOnlinePlayers().flatMap { players =>
      if (applicationConfig.hideMods) filterUsersByPermission(players)
      else Success(players)
    }
  }
}
