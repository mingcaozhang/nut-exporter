import sbt._

object Dependencies {

  object Versions {
    val Akka = "2.6.8"
    val AkkaHttp = "10.2.4"
    val LogbackClassic = "1.2.3"
    val PureConfig = "0.14.1"
    val ScalaLogging = "3.9.2"
    val Scalatest = "3.2.5"
  }

  lazy val dependencies: Seq[ModuleID] = deps ++ testDeps

  lazy val deps: Seq[ModuleID] = Seq(
    "ch.qos.logback" % "logback-classic" % Versions.LogbackClassic,
    "com.github.pureconfig" %% "pureconfig" % Versions.PureConfig,
    "com.typesafe.akka" %% "akka-actor-typed" % Versions.Akka,
    "com.typesafe.akka" %% "akka-stream" % Versions.Akka,
    "com.typesafe.akka" %% "akka-http" % Versions.AkkaHttp,
    "com.typesafe.scala-logging" %% "scala-logging" % Versions.ScalaLogging
  )

  lazy val testDeps: Seq[ModuleID] = Seq("org.scalatest" %% "scalatest" % Versions.Scalatest).map(_ % "test")
}
