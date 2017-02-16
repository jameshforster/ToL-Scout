package connectors

import java.util.logging.Logger

import com.google.inject.{Inject, Singleton}
import exceptions.ServerNotRespondingException
import org.spongepowered.api.{Game, Server}

import scala.util.{Failure, Success, Try}

@Singleton
class GameConnector @Inject()(game: Game, logger: Logger) {

  def fetchServer(): Try[Server] = Try {
    game.getServer
  } match {
    case Success(server) => Success(server)
    case Failure(error) =>
      logger.warning(error.getMessage)
      Failure(ServerNotRespondingException())
  }
}
