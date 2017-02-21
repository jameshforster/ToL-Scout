package controllers

import java.net.SocketTimeoutException

import com.google.inject.{Inject, Singleton}
import org.apache.http.{ConnectionClosedException, ExceptionLogger}
import org.apache.http.config.SocketConfig
import org.apache.http.impl.bootstrap.ServerBootstrap
import org.apache.http.ssl.SSLContexts
import org.slf4j.Logger

@Singleton
class UserController @Inject()(stdErrorExceptionLogger: StdErrorExceptionLogger) {

  val socketConfig = SocketConfig.custom()
    .setSoTimeout(15000)
    .setTcpNoDelay(true)
    .build()

  val sslcontext = SSLContexts.createDefault()

  val server = ServerBootstrap.bootstrap()
    .setListenerPort(7000)
    .setServerInfo("Test/1.1")
    .setSocketConfig(socketConfig)
    .setSslContext(sslcontext)
    .setExceptionLogger(stdErrorExceptionLogger)
    .registerHandler("*", new HttpFileHandler(docRoot))
    .create()
}

@Singleton
class StdErrorExceptionLogger @Inject()(logger: Logger) extends ExceptionLogger {

  @Override
  def log(ex :Exception): Unit = {
    ex match {
      case timeOutException: SocketTimeoutException => logger.error("Socket connection has timed out!")
      case connectionClosedException: ConnectionClosedException => logger.error("Lost connection with base!")
      case exception: Exception => logger.warn(exception.getMessage)
    }
  }
}
