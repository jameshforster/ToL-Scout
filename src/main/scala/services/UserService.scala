package services

import com.google.inject.{Inject, Singleton}
import connectors.GameConnector
import handlers.ServerRequestHandler
import org.spongepowered.api.entity.living.player.Player

import collection.JavaConverters._
import scala.util.{Failure, Success, Try}

@Singleton
class UserService @Inject()(gameConnector: GameConnector, serverRequestHandler: ServerRequestHandler) {

  def fetchOnlineUsers(): Try[List[Player]] = {
    serverRequestHandler.handleRequest {
      gameConnector.fetchServer() match {
        case Success(server) => server.getOnlinePlayers.asScala.toList
        case Failure(error) => throw error
      }
    }
  }
}
