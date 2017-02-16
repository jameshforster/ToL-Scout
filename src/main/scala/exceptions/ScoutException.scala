package exceptions

class ScoutException(message: String, code: Int) extends Exception(message)

case class NotStartedException() extends ScoutException("Server has not finished starting", 503)
