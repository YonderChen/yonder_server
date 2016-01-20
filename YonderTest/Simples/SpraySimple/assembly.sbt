import sbtassembly.Plugin._
import AssemblyKeys._

assemblySettings

jarName in assembly := "SpraySimple.jar"

name := "SpraySimple"

version := "1.0"

scalaVersion  := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")