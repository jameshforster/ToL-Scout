package connectors

import com.google.inject.{Inject, Singleton}
import exceptions.ServerNotRespondingException
import org.slf4j.Logger
import org.spongepowered.api.{Game, Server}

import scala.util.{Failure, Success, Try}

@Singleton
class GameConnector @Inject()(game: Game, logger: Logger) {

  def fetchServer(): Try[Server] = Try {
    game.getServer
  } match {
    case Success(server) => Success(server)
    case Failure(error) =>
      logger.warn(error.getMessage)
      Failure(ServerNotRespondingException())
  }
}
