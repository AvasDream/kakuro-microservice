name        := "kakuro-microservice"
version       := "0.0.2"
scalaVersion  := "2.11.8"
scalaSource in Compile := baseDirectory.value / "src/"

// Some this needed dependencies resolver for akka-stream but fml anyway
resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.8" 
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.19"
// https://mvnrepository.com/artifact/com.typesafe.play/play-json_2.11
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.10"
libraryDependencies += "com.ibm" %% "couchdb-scala" % "0.5.1"