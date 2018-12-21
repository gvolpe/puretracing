import Dependencies._

lazy val commonScalacOptions = Seq(
  "-language:existentials",
  "-language:higherKinds",
  "-unchecked",
  "-Ypartial-unification",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-value-discard"
)

inThisBuild(Seq(
  scalacOptions := commonScalacOptions,
  addCompilerPlugin(Libraries.betterMonadicFor),
  addCompilerPlugin(Libraries.kindProjector)
))

lazy val api = project
lazy val cats = project.dependsOn(api).settings(
  libraryDependencies ++= Seq(
    Libraries.catsCore,
    Libraries.catsEffect,
    Libraries.catsMtlCore
  )
)
lazy val `cats-opentracing` = project.dependsOn(cats)
  .settings(libraryDependencies += Libraries.openTracingApi)

lazy val `puretracing-http4s` = project.in(file("http4s"))
  .dependsOn(api, cats).settings(
    libraryDependencies ++= Seq(
      Libraries.http4sDsl
    )
)

// TODO: Example that sends HTTP requests
lazy val exampleLib = project.in(file("examples/lib")).dependsOn(cats).settings(libraryDependencies += Libraries.catsEffect)
lazy val exampleAppNoTrace = project.in(file("examples/app-no-trace")).dependsOn(exampleLib)
lazy val examplePrintlnTracing = project.in(file("examples/app-println-tracing")).dependsOn(exampleLib)
lazy val exampleOpenTracing = project.in(file("examples/app-open-tracing")).dependsOn(exampleLib, `cats-opentracing`)
  .settings(libraryDependencies += Libraries.jaegerClient)