import sbt.Keys._
import sbt._

object ScalaBCrypt extends Build {
  val Organization        = "Ricardo"
  val Name                = "ScalaBCrypt"
  val Version             = "0.1.0-SNAPSHOT"
  val ScalaVersion        = "2.11.7"
  val ScalatraVersion     = "2.4.0"
  val ScallikeJDBCVersion = "2.3.5"

  lazy val project = Project(
    "scalaBCrypt",
    file("."),
    settings =
      Seq(
        organization := Organization,
        scalacOptions ++= Seq("-unchecked", "-deprecation"),
        name := Name,
        version := Version,
        scalaVersion := ScalaVersion,
        resolvers += Classpaths.typesafeReleases,
        resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
        libraryDependencies ++= Seq(
          "org.scalatest" %% "scalatest" % "2.2.6" % "test",
          "org.scalacheck" %% "scalacheck" % "1.12.5" % "test",
          "org.scala-lang.modules" %% "scala-xml" % "1.0.4"
        ),
        testOptions in Test += Tests.Argument("-oD")
      )
  )
}

