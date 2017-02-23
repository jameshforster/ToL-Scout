package handlers

import com.google.inject.{Inject, Singleton}
import exceptions.ServerNotStartedException
import messages.ExceptionMessages._
import org.slf4j.Logger
import org.spongepowered.api.Game

import scala.util.{Failure, Try}

@Singleton
class ServerRequestHandler @Inject()(game: Game, logger: Logger) {

  def handleRequest[T](f: => Try[T]): Try[T] = {
    if (game.isServerAvailable) f
    else {
      logger.warn(notStarted)
      Failure(ServerNotStartedException())
    }
  }
}
