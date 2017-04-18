import com.typesafe.sbt.packager.docker._

name := "$name$"

version := "$project_version$"

scalaVersion := "$scala_version$"

updateOptions := updateOptions.value.withCachedResolution(true)

// old approach, use sbt-revolver instead (as global plugin)
// fork in run := true
// cancelable in Global := true

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test" withSources() withJavadoc(),
  "com.typesafe.akka" %% "akka-http" % "10.0.4",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.4",
  "com.typesafe" % "config" % "1.3.1"
)

enablePlugins(DockerPlugin)
enablePlugins(JavaAppPackaging)

dockerCommands := Seq(
  Cmd("FROM", "java:8-jre"),
  Cmd("WORKDIR", "/opt/docker"),
  ExecCmd("ADD", "opt", "/opt"),
  ExecCmd("RUN", "chown", "-R", "daemon:daemon", "/opt"),
  Cmd("USER", "daemon"),
  Cmd("EXPOSE", "8080"),
  ExecCmd("ENTRYPOINT", "java", "-cp", "/opt/docker/lib/*"),
  ExecCmd("CMD", "$package$.App")
)

packageName in Docker := "$name$"
dockerUpdateLatest := true
dockerRepository := Some("$docker_repository$")

// run tests before publishing docker image
(stage in Docker) := {
  val dummy = (test in Test).value
  (stage in Docker).value
}
