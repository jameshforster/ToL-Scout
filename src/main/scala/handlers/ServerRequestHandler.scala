package handlers

import com.google.inject.{Inject, Singleton}
import exceptions.NotStartedException
import org.spongepowered.api.Game

import scala.util.{Failure, Try}

@Singleton
class ServerRequestHandler @Inject()(game: Game) {

  def handleRequest[T](f: => T): Try[T] = {
    if (game.isServerAvailable) Try(f)
    else Failure(NotStartedException())
  }
}
