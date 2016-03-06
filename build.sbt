name := "Merkle Treat"

organization := "com.constructiveproof"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.10.6", "2.11.7")

libraryDependencies ++= Seq(
  "com.roundeights" %% "hasher" % "1.2.0",
  "org.mockito" % "mockito-all" % "1.10.5" % "test",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.11.5" % "test"
)

initialCommands := "import com.constructiveproof.merkletreat._"
