package exceptions


case class NotStartedException() extends Exception("Server has not finished starting")
