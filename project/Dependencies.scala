import sbt._

object Dependencies {

  object Versions {
    val catsCore    = "1.5.0"
    val catsEffect  = "1.1.0"
    val catsMtl     = "0.4.0"
    val fs2         = "1.0.2"
    val http4s      = "0.20.0-M4"
    val circe       = "0.11.0"

    val openTracing = "0.31.0"
    val jaeger      = "0.32.0"

    // Test
    val scalaTest   = "3.0.5"
    val scalaCheck  = "1.13.5"

    // Compiler
    val kindProjector     = "0.9.9"
    val betterMonadicFor  = "0.2.4"
  }

  object Libraries {
    def circe(artifact: String): ModuleID   = "io.circe"    %% s"circe-$artifact" % Versions.circe
    def http4s(artifact: String): ModuleID  = "org.http4s"  %% s"http4s-$artifact" % Versions.http4s

    lazy val catsCore       = "org.typelevel" %% "cats-core"        % Versions.catsCore
    lazy val catsEffect     = "org.typelevel" %% "cats-effect"      % Versions.catsEffect
    lazy val catsMtlCore    = "org.typelevel" %% "cats-mtl-core"    % Versions.catsMtl
    lazy val fs2Core        = "co.fs2"        %% "fs2-core"         % Versions.fs2

    lazy val http4sServer   = http4s("blaze-server")
    lazy val http4sClient   = http4s("blaze-client")
    lazy val http4sDsl      = http4s("dsl")
    lazy val http4sCirce    = http4s("circe")

    lazy val circeCore      = circe("core")
    lazy val circeGeneric   = circe("generic")
    lazy val circeGenericX  = circe("generic-extras")

    lazy val openTracingApi = "io.opentracing" % "opentracing-api" % Versions.openTracing
    lazy val jaegerClient   = "io.jaegertracing" % "jaeger-client" % Versions.jaeger

    // Test
    lazy val scalaCheck     = "org.scalacheck"        %% "scalacheck"       % Versions.scalaCheck
    lazy val scalaTest      = "org.scalatest"         %% "scalatest"        % Versions.scalaTest

    // Compiler
    lazy val kindProjector    = "org.spire-math"      %% "kind-projector"     % Versions.kindProjector // cross CrossVersion.full
    lazy val betterMonadicFor = "com.olegpy"          %% "better-monadic-for" % Versions.betterMonadicFor
  }

}