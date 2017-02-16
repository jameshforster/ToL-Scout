package handlers

import java.util.logging.Logger

import com.google.inject.{Inject, Singleton}
import exceptions.ServerNotStartedException
import org.spongepowered.api.Game
import messages.ExceptionMessages._

import scala.util.{Failure, Try}

@Singleton
class ServerRequestHandler @Inject()(game: Game, logger: Logger) {

  def handleRequest[T](f: => T): Try[T] = {
    if (game.isServerAvailable) Try(f)
    else {
      logger.warning(notStarted)
      Failure(ServerNotStartedException())
    }
  }
}
