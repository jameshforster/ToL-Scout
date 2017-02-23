package handlers

import exceptions.{ServerNotRespondingException, ServerNotStartedException}
import helpers.TestSpec
import models.UserModel
import org.apache.http.protocol.HttpContext
import org.apache.http.{HttpRequest, HttpResponse}
import services.UserService
import org.mockito.Mockito._

import scala.util.{Failure, Success, Try}

class UserListRequestHandlerSpec extends TestSpec {

  def setupService(response: Try[List[UserModel]]): UserService = {
    val mockService = mock[UserService]

    when(mockService.fetchOnlineUsers())
      .thenReturn(response)

    mockService
  }

  "The userListRequestHandler" should {

    "handle a successful response from the plugin" in {
      val response = Success(List(UserModel("name1"), UserModel("name2")))
      lazy val service = setupService(response)
      lazy val handler = new UserListRequestHandler(service)
      lazy val result = handler.handle(mock[HttpRequest], mock[HttpResponse], mock[HttpContext])

      result shouldBe {}
    }

    "handle a ServerNotRespondingException" in {
      val response = Failure(ServerNotRespondingException())
      lazy val service = setupService(response)
      lazy val handler = new UserListRequestHandler(service)
      lazy val result = handler.handle(mock[HttpRequest], mock[HttpResponse], mock[HttpContext])

      result shouldBe {}
    }

    "handle a ServerNotStartedException" in {
      val response = Failure(ServerNotStartedException())
      lazy val service = setupService(response)
      lazy val handler = new UserListRequestHandler(service)
      lazy val result = handler.handle(mock[HttpRequest], mock[HttpResponse], mock[HttpContext])

      result shouldBe {}
    }

    "handle a generic exception" in {
      val response = Failure(new Exception(""))
      lazy val service = setupService(response)
      lazy val handler = new UserListRequestHandler(service)
      lazy val result = handler.handle(mock[HttpRequest], mock[HttpResponse], mock[HttpContext])

      result shouldBe {}
    }
  }
}
