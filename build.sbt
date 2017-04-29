import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "io.github.pkoryzna",
      scalaVersion := "2.12.2",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "kindle-kitchen-display",
    libraryDependencies ++= Seq(
      latoFont,
      fontAwesome,
      scalaTest % Test
    )
  )
