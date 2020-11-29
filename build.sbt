import play.sbt.PlayJava

name := """KunderaDataBaseCloud"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayNettyServer, PlayEnhancer)

scalaVersion := "2.13.4"

resolvers += Resolver.url("Typesafe Ivy releases", url("https://repo.typesafe.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
  cache,
  javaCore,
  filters,
  "com.impetus.kundera.client" % "kundera-mongo" % "3.13",
  "com.google.code.gson" % "gson" % "2.8.6",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.12.0",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.12.0",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.12.0",
  "org.projectlombok" % "lombok" % "1.18.16"
)


routesGenerator := InjectedRoutesGenerator
