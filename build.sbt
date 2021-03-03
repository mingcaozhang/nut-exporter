import com.typesafe.sbt.packager.docker._

lazy val root = Project("nut-monitor", file("."))
  .enablePlugins(AshScriptPlugin, JavaAppPackaging)
  .settings(
    name := "nut-monitor",
    organization := "zhangmin",
    scalafmtOnCompile := true,
    libraryDependencies ++= Dependencies.dependencies,
    dockerSettings
  )

lazy val dockerSettings = Seq(
  dockerBaseImage := "instantlinux/nut-upsd:latest",
  packageName in Docker := "mingcaozhang/nut-exporter",
  dockerCommands ++= Seq(Cmd("USER", "root"), ExecCmd("RUN", "apk", "add", "openjdk8-jre"))
)
