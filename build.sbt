lazy val root = Project("nut-monitor", file(".")).settings(
  name := "nut-monitor",
  scalafmtOnCompile := true,
  libraryDependencies ++= Dependencies.deps
)
