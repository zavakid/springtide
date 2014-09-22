import sbt._
import Keys._
import org.sbtidea.SbtIdeaPlugin._
import xerial.sbt.Pack._
import com.zavakid.sbt.OneLog.OneLogKeys._

object SpringtideBuild extends Build {
  import BuildSettings._
  import Dependencies._

  lazy val parent = Project(id = "springtide-root",
    base = file("."))
    .aggregate (core, example)
    .settings(basicSettings: _*)

  lazy val core = Project(id = "springtide-core", base = file("core"))
    .settings(coreSettings: _*)
    .settings(libraryDependencies ++=
      coreDependencies)
    .settings(oneLogSettings: _*)

  lazy val example = Project(id = "springtide-example", 
    base = file("example"))
    .aggregate(todolist)
    .settings(basicSettings: _*)

  lazy val todolist = Project(id = "springtide-example-todolist",
    base = file("example/todolist"))
    .settings(libraryDependencies ++=
            todolistDependencies)
    .settings(todolistSettings : _*)
    .settings(oneLogSettings: _*)

}

object BuildSettings {
  lazy val ScalaVersion = "2.11.2"

  lazy val basicSettings = seq(
    organization          := "com.zavakid.springtide",
    startYear             := Some(2014),
    licenses              := Seq("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    scalaVersion          := ScalaVersion,
    ideaExcludeFolders := ".idea" :: ".idea_modules" :: Nil,
    // sbt-idea: donot download javadoc, we donot like them
    transitiveClassifiers in Global := Seq(Artifact.SourceClassifier),
    testOptions in Test := Seq(Tests.Filter(s => s.endsWith("Test") || s.endsWith("Spec"))),
    scalacOptions := Seq(
      "-deprecation",
      "-unchecked",
      "-encoding", "UTF-8",
      "-target:jvm-1.7",
      "-Xlint",
      "-Yclosure-elim",
      "-Yinline",
      "-feature",
      "-language:postfixOps"
      // "-optimise"   // this option will slow our build
      ),
    javacOptions := Seq(
      "-target", "1.7" ,
      "-source", "1.7",
      "-Xlint:unchecked",
      "-Xlint:deprecation"
      ),
    //externalResolvers <<= resolvers map { rs =>
    //  Resolver.withDefaultResolvers(rs, mavenCentral = false)
    //},
    resolvers ++= Seq(
        "99-empty" at "http://version99.qos.ch/",
        "Akka Repo" at "http://repo.akka.io/repository"
      )
    ) ++ net.virtualvoid.sbt.graph.Plugin.graphSettings ++ packSettings 

  lazy val coreSettings = basicSettings
  lazy val todolistSettings = basicSettings ++ Seq(
    //packMain := Map("" -> "com.mogujie.dragon.gaia.web.GaiaRestLauncher")
    //,packExtraClasspath := Map("gaiaRest" -> Seq("${PROG_HOME}/conf"))
    )
}

object Dependencies {
  val SpringVersion = "4.1.0.RELEASE"
  val SpringDataJpaVersion = "1.7.0.RELEASE"
  val JettyVersion = "9.2.3.v20140905"
  val AkkaVersion = "2.3.6"
  val ScalatraVersion = "2.3.0"

  def basicDependencies:Seq[ModuleID] = Seq(
      "org.hibernate.javax.persistence" % "hibernate-jpa-2.1-api" % "1.0.0.Final",
      "com.typesafe" % "config" % "1.2.1",
      "joda-time" % "joda-time" % "2.4",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
      "net.sf.dozer" % "dozer" % "5.5.1"  
    ) ++ akka ++ jackson ++ spring ++ springWeb

  def coreDependencies:Seq[ModuleID] = basicDependencies ++ Seq(
  )

  def todolistDependencies = basicDependencies ++ jetty ++ database ++ Seq(
  )

  // commons dependency
  lazy val spring = Seq(
    "org.springframework" % "spring-tx" % SpringVersion,
    "org.springframework" % "spring-context" % SpringVersion,
    "org.springframework" % "spring-aop" % SpringVersion,
    "org.springframework" % "spring-jdbc" % SpringVersion,
    "org.springframework" % "spring-orm" % SpringVersion,
    "org.springframework" % "spring-test" % SpringVersion % "test",
    "org.springframework.data" % "spring-data-jpa" % SpringDataJpaVersion
  )

  lazy val springWeb = Seq(
    "org.springframework" % "spring-web" % SpringVersion,
    "org.springframework" % "spring-webmvc" % SpringVersion
  )

  lazy val jetty = Seq(
    //"org.eclipse.jetty" % "jetty-webapp" % JettyVersion
    "org.eclipse.jetty" % "jetty-webapp" % JettyVersion,
    "org.eclipse.jetty" % "jetty-annotations" % JettyVersion,
    "javax.servlet" % "javax.servlet-api" % "3.1.0"
  )

  lazy val database = Seq(
    "mysql" % "mysql-connector-java" % "5.1.32" ,
    "com.alibaba" % "druid" % "1.0.9",
    "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final"
  )

  lazy val jackson = Seq(
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.2",
    "com.fasterxml.jackson.core" % "jackson-annotations" % "2.4.2",
    "com.fasterxml.jackson.core" % "jackson-core" % "2.4.2"
  )

  lazy val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % AkkaVersion
  )

  lazy val scalatra = Seq(
    "org.scalatra" %% "scalatra" % ScalatraVersion,
    "org.scalatra" %% "scalatra-scalate" % ScalatraVersion,
    "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test"
  )

}
