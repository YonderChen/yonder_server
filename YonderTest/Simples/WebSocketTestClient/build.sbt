
name := "WebSocketTestClient"

version := "1.0"

scalaVersion  := "2.10.4"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Spray repository" at "http://repo.spray.io/"
)

libraryDependencies ++= {
  val akkaV = "2.2.3"
  val sprayV = "1.2.0"
  Seq(
    "com.wandoulabs.akka" %% "spray-websocket" % "0.1.1-RC1",
    "org.java-websocket"  %   "Java-WebSocket" % "1.3.0",
    "io.spray"            %   "spray-can"      % sprayV,
    "io.spray"            %   "spray-routing"  % sprayV,
    "io.spray"            %   "spray-http"     % sprayV,
    "io.spray"            %   "spray-util"     % sprayV,
    "io.spray"            %   "spray-httpx"     % sprayV,
    "com.typesafe.akka"   %%  "akka-actor"     % akkaV,
    "org.scalatest"       %%  "scalatest"      % "2.0"   % "test",
    "junit"               %   "junit"          % "4.11"  % "test",
    "org.specs2"          %%  "specs2"         % "2.2.3" % "test"
  )
}








