import java.util.logging.Logger

import com.google.inject.{Inject, Singleton}
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameStartedServerEvent
import org.spongepowered.api.plugin.Plugin

@Plugin(id = "tol_scout_plugin", name = "Scout Plugin", version = "0.1.0") @Singleton
class ScoutPlugin @Inject()(logger: Logger) {

  @Listener
  def onServerStart(event: GameStartedServerEvent) {
    logger.info("Scout has been deployed into the field!")
  }
}
