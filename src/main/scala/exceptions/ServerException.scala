package exceptions

class ServerException(message: String, code: Int) extends Exception(message)

case class NotStartedException() extends ServerException("Server has not finished starting", 503)
