package handlers

import com.google.inject.{Inject, Singleton}
import org.spongepowered.api.Game

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

@Singleton
class ServerRequestHandler @Inject()(game: Game) {
  
}
