name := "scala-stream-kafka-test"

version := "1.0"

scalaVersion := "2.11.8"

val libraries: Seq[ModuleID] = Seq(
  "com.softwaremill.reactivekafka" %% "reactive-kafka-core" % "0.10.0",
  "com.typesafe.play"               % "play-json_2.11"      % "2.4.3"
)

libraryDependencies ++= libraries
