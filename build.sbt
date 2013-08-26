name := "tiberius"

version := "0.1.0"

scalaVersion := "2.10.2"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature"
)

resolvers ++= Seq(
  "jline" at "http://jline.sourceforge.net/m2repo"
)

libraryDependencies ++= Seq(
  "jline"      %  "jline"  %  "0.9.9",
  "org.specs2" %% "specs2" %  "2.1.1"  %  "test"
)
