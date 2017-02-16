package exceptions

import keys.ResponseKeys._
import messages.ExceptionMessages._

class ServerException(message: String, code: Int) extends Exception(message)

case class ServerNotStartedException() extends ServerException(notStarted, NOT_FOUND)

case class ServerNotRespondingException() extends ServerException(noResponse, NO_RESPONSE)
