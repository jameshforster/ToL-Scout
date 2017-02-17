import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.0"
  lazy val mockito = "org.mockito" % "mockito-core" % "1.8.5"
  lazy val sponge = "org.spongepowered" % "spongeapi" % "5.0.0"
  lazy val apacheHttpCore = "org.apache.httpcomponents" % "httpcore" % "4.4.6"
  lazy val apacheHttpClient = "org.apache.httpcomponents" % "httpclient" % "4.5.3"
}
