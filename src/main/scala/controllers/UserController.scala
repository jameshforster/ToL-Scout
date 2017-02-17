package controllers

import org.apache.http.{HttpRequest, HttpResponse}
import org.apache.http.protocol._

class UserController {

  val httpProcessor = new HttpProcessor {
    override def process(response: HttpResponse, context: HttpContext) = ???

    override def process(request: HttpRequest, context: HttpContext) = ???
  }

  val requestHandler = new HttpRequestHandler {
    override def handle(request: HttpRequest, response: HttpResponse, context: HttpContext) = ???
  }

  val handlerMapper = new UriHttpRequestHandlerMapper()

  handlerMapper.register("/scout/online-users", requestHandler)

  val httpService = new HttpService(httpProcessor, handlerMapper)
}
