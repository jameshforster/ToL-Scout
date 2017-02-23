package handlers

import java.net.SocketTimeoutException

import com.google.inject.{Inject, Singleton}
import org.apache.http.{ConnectionClosedException, ExceptionLogger}
import org.slf4j.Logger

@Singleton
class StdHttpExceptionHandler @Inject()(logger: Logger) extends ExceptionLogger {

  @Override
  def log(ex :Exception): Unit = {
    ex match {
      case timeOutException: SocketTimeoutException => logger.error("Socket connection has timed out!")
      case connectionClosedException: ConnectionClosedException => logger.error("Lost connection with base!")
      case exception: Exception => logger.warn(exception.getMessage)
    }
  }
}
