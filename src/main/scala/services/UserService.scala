package services

import com.google.inject.{Inject, Singleton}
import connectors.GameConnector
import handlers.ServerRequestHandler
import org.spongepowered.api.entity.living.player.Player

import scala.collection.JavaConverters._
import scala.util.{Success, Try}

@Singleton
class UserService @Inject()(gameConnector: GameConnector, serverRequestHandler: ServerRequestHandler) {

  def fetchOnlineUsers(): Try[List[Player]] = {
    serverRequestHandler.handleRequest {
      gameConnector.fetchServer().flatMap { server =>
        Success(server.getOnlinePlayers.asScala.toList)
      }
    }
  }
}
