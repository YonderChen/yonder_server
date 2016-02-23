name := "Spring-Scala-Simple"

version := "1.0"

scalaVersion := "2.10.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "SpringSource Milestone Repository" at "http://repo.springsource.org/milestone/")

libraryDependencies ++= {
  val springscala = "1.0.0.RC1"
  val springV = "3.2.4.RELEASE"
  Seq(
    "org.springframework.scala" %% "spring-scala" % springscala,
    "org.springframework" % "spring-jdbc" % springV,
    "commons-dbcp" % "commons-dbcp" % "1.4",
    "mysql" % "mysql-connector-java" % "5.1.26",
    "aspectj" % "aspectjweaver" % "1.5.4",
    "org.scalatest" %% "scalatest" % "2.0" % "test",
    "junit" % "junit" % "4.11" % "test")
}