package puretracing.http4s

import cats.data.Kleisli
import cats.effect.Sync
import cats.syntax.all._
import org.http4s.syntax.StringSyntax
import org.http4s.{Header, HttpApp}
import puretracing.api.Propagation
import puretracing.cats.dsl._

object Http4sTracer extends StringSyntax {

  private[http4s] val spanHeader = "trace-id"

  def apply[F[_]: Propagation: Sync](http: HttpApp[F]): HttpApp[F] =
    Kleisli { req =>
      for {
        span <- Propagation[F].currentSpan
        tr   <- Sync[F].delay(req.putHeaders(Header(spanHeader, span.toString)))
        _    <- inChildSpan[F]("http4s-tracer")(_.log("request" -> s"$req"))
        rs   <- http(tr)
        _    <- inChildSpan[F]("http4s-tracer")(_.log("response" -> s"$rs"))
      } yield rs
    }

}
