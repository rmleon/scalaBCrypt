import sbt.Keys._
import sbt._

object ScalaBCrypt extends Build {
  val Organization        = "Ricardo"
  val Name                = "ScalaBCrypt"
  val Version             = "0.1.1-SNAPSHOT"
  val ScalaVersion        = "2.12.1"
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
          "org.scalatest" %% "scalatest" % "3.0.8" % "test",
          "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"
        ),
        testOptions in Test += Tests.Argument("-oD")
      )
  )
}

