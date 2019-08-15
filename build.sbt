
val Organization        = "Ricardo"
val Name                = "ScalaBCrypt"
val Version             = "0.1.1-SNAPSHOT"
val ScalaVersion        = "2.12.1"

lazy val root = (project in file("."))
  .settings(
      name := "scalaBCrypt",
      organization := Organization,
      scalacOptions ++= Seq("-unchecked", "-deprecation"),
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "3.0.8" % Test,
        "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
      ),
      testOptions in Test += Tests.Argument("-oD")
    )

