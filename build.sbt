lazy val root = Project("nut-monitor", file(".")).settings(
  name := "nut-monitor",
  organization := "zhangmin",
  scalafmtOnCompile := true,
  libraryDependencies ++= Dependencies.dependencies
)
