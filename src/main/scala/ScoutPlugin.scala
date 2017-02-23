import com.google.inject.{Inject, Singleton}
import controllers.ServerController
import org.slf4j.Logger
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.GameReloadEvent
import org.spongepowered.api.event.game.state.{GameStartedServerEvent, GameStoppedEvent, GameStoppingEvent}
import org.spongepowered.api.plugin.Plugin

@Plugin(id = "tol_scout_plugin", name = "Scout Plugin", version = "0.1.0")
@Singleton
class ScoutPlugin @Inject()(logger: Logger, serverController: ServerController) {

  @Listener
  def onServerStart(event: GameStartedServerEvent) {
    serverController.startServer()
    logger.info("Scout has been deployed into the field!")
  }

  @Listener
  def onServerStopping(event: GameStoppingEvent): Unit = {
    serverController.stopServer()
    logger.info("Scout is moving to extraction point.")
  }

  @Listener
  def onServerStopped(event: GameStoppedEvent): Unit = {
    logger.info("Scout has been extracted.")
  }
}
