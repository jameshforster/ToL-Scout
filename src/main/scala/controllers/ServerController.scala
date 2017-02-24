package controllers

import com.google.inject.{Inject, Singleton}
import config.AppConfig
import handlers.{StdHttpExceptionHandler, UserListRequestHandler}
import org.apache.http.config.SocketConfig
import org.apache.http.impl.bootstrap.ServerBootstrap
import org.apache.http.ssl.SSLContexts

@Singleton
class ServerController @Inject()(stdHttpExceptionLogger: StdHttpExceptionHandler,
                                 userListRequestHandler: UserListRequestHandler,
                                 applicationConfig: AppConfig) {

  private val socketConfig = SocketConfig.custom()
    .setSoTimeout(15000)
    .setTcpNoDelay(true)
    .build()

  private val sslContext = SSLContexts.createDefault()

  private lazy val server = ServerBootstrap.bootstrap()
    .setListenerPort(applicationConfig.listenerPort)
    .setServerInfo("Test/1.1")
    .setSocketConfig(socketConfig)
    .setSslContext(sslContext)
    .setExceptionLogger(stdHttpExceptionLogger)
    .registerHandler("*", userListRequestHandler)
    .create()

  def startServer(): Unit = {
    server.start()
  }

  def stopServer(): Unit = {
    server.stop()
  }
}
