import Dependencies._

inThisBuild(Seq(
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

lazy val sttp = project.dependsOn(api)
  .settings(libraryDependencies += "com.softwaremill.sttp" %% "core" % "1.5.2")

lazy val exampleLib = project.in(file("examples/lib")).dependsOn(cats).settings(libraryDependencies += Libraries.catsEffect)
lazy val exampleAppNoTrace = project.in(file("examples/app-no-trace")).dependsOn(exampleLib)
lazy val examplePrintlnTracing = project.in(file("examples/app-println-tracing")).dependsOn(exampleLib)
lazy val exampleOpenTracing = project.in(file("examples/app-open-tracing")).dependsOn(exampleLib, `cats-opentracing`)
  .settings(libraryDependencies += Libraries.jaegerClient)
lazy val exampleSttpCats = project.in(file("examples/app-sttp")).dependsOn(sttp, `cats-opentracing`).settings(libraryDependencies ++= Seq(
  "com.softwaremill.sttp" %% "async-http-client-backend-cats" % "1.5.2",
  "io.jaegertracing" % "jaeger-client" % "0.32.0"
))
