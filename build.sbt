name := "auth-service"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc
  , anorm
  , cache
  , "com.nulab-inc" %% "play2-oauth2-provider" % "0.4.0"
  , "com.typesafe.slick" %% "slick" % "2.0.0"
  , "com.typesafe.play" %% "play-slick" % "0.6.0.1"
)     

play.Project.playScalaSettings

scalariformSettings
