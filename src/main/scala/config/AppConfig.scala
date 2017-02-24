package config

import com.google.inject.{Inject, Singleton}
import ninja.leaping.configurate.ConfigurationNode
import ninja.leaping.configurate.commented.CommentedConfigurationNode
import ninja.leaping.configurate.loader.ConfigurationLoader
import org.slf4j.Logger
import org.spongepowered.api.config.DefaultConfig

import scala.util.{Failure, Try}

@Singleton
@DefaultConfig(sharedRoot = true)
class ApplicationConfig @Inject()(logger: Logger, loader: ConfigurationLoader[CommentedConfigurationNode]) extends AppConfig {

  private val rootNode: Try[ConfigurationNode] = Try {
    loader.load()
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