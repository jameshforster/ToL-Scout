package config

import java.nio.file.Path

import com.google.inject.{Inject, Singleton}
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.commented.CommentedConfigurationNode
import ninja.leaping.configurate.hocon.HoconConfigurationLoader
import ninja.leaping.configurate.loader.ConfigurationLoader
import org.slf4j.Logger
import org.spongepowered.api.Sponge
import org.spongepowered.api.config.DefaultConfig

import scala.util.{Failure, Try}

@Singleton
@DefaultConfig(sharedRoot = true)
class ApplicationConfig @Inject()(logger: Logger, path: Path) extends AppConfig {

  private val rootNode: Try[ConfigurationNode] = Try {
    val loader: ConfigurationLoader[CommentedConfigurationNode] = HoconConfigurationLoader.builder().setPath(path).build()
    val jarConfigFile = Sponge.getAssetManager.getAsset("defaultConfig.conf").get().getUrl
    val defaultLoader: ConfigurationLoader[CommentedConfigurationNode] = HoconConfigurationLoader.builder().setURL(jarConfigFile).build()
    val defaultNode = defaultLoader.load()

    loader.load().mergeValuesFrom(defaultNode)
  } match {
    case Failure(exception) =>
      logger.error(exception.getMessage)
      Failure(exception)
    case success => success
  }

  private def getConfig[T](keys: String*): Try[T] = {
    rootNode.map { root =>
      keys.foldLeft[ConfigurationNode](root) {
        (node, key) => node.getNode(key)
      }.asInstanceOf[T]
    } recoverWith {
      case error =>
        logger.warn(error.getMessage)
        Failure(error)
    }
  }

  implicit private val getInt: ConfigurationNode => Int = config => config.getInt

  val listenerPort: Int = getConfig[Int]("server", "port").get
}

trait AppConfig {
  val listenerPort: Int
}