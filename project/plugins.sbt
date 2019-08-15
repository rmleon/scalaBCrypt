logLevel := Level.Warn

resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"

addSbtPlugin("io.stryker-mutator" % "sbt-stryker4s" % "0.6.1")
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.8.3")
