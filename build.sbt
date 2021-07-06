import play.sbt.PlayJava

name := """KunderaDataBaseCloud"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayNettyServer, PlayEnhancer)

scalaVersion := "2.13.6"

resolvers += Resolver.url("Typesafe Ivy releases", url("https://repo.typesafe.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
  cache,
  javaCore,
  filters,
  "com.impetus.kundera.client" % "kundera-mongo" % "3.13",
  "com.google.code.gson" % "gson" % "2.8.7",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.12.4",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.12.3",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.12.4",
  "org.projectlombok" % "lombok" % "1.18.20"
)


routesGenerator := InjectedRoutesGenerator
