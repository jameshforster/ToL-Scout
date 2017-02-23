package handlers

import com.google.inject.{Inject, Singleton}
import exceptions.{ServerNotRespondingException, ServerNotStartedException}
import org.apache.http.entity.StringEntity
import org.apache.http.protocol.{HttpContext, HttpRequestHandler}
import org.apache.http.{HttpRequest, HttpResponse}
import services.UserService

import scala.util.{Failure, Success}

@Singleton
class UserListRequestHandler @Inject()(userService: UserService) extends HttpRequestHandler {
  override def handle(request: HttpRequest, response: HttpResponse, context: HttpContext): Unit = {
    userService.fetchOnlineUsers() match {
      case Success(users) =>
        response.setStatusCode(200)
        response.setEntity(new StringEntity(users))
      case Failure(notRespondingException: ServerNotRespondingException) =>
        response.setStatusCode(504)
        response.setEntity(new StringEntity(notRespondingException.getMessage))
      case Failure(notStartedException: ServerNotStartedException) =>
        response.setStatusCode(502)
        response.setEntity(new StringEntity(notStartedException.getMessage))
      case Failure(error) =>
        response.setStatusCode(500)
        response.setEntity(new StringEntity(error.getMessage))
    }
  }
}
