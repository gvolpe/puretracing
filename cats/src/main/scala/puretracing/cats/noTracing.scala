package puretracing.cats

import cats.Applicative
import puretracing.api.{Propagation, TracingValue}

/**
  * Default implementation if user doesn't wish to trace
  */
object noTracing {
  implicit def noTracingPropagationInstance[F[_]: Applicative]: Propagation[F] = new NoopPropagation[F]
}

class NoopPropagation[F[_]](implicit F: Applicative[F]) extends Propagation[F] {
  override type Span = Unit
  private val dummy = F.pure(())
  private val dummyHeaders = F.pure(Map.empty[String, String])

  override def currentSpan: F[Unit] = dummy
  override def useSpanIn[A](span: Unit)(fa: F[A]): F[A] = fa
  override def startRootSpan(operationName: String, upstreamSpan: Headers): F[Unit] = dummy
  override def export(span: Unit): F[Headers] = dummyHeaders
  override def startChild(span: Unit, operationName: String): F[Unit] = dummy
  override def finish(span: Unit): F[Unit] = dummy
  override def setTag(span: Unit, key: String, v: TracingValue): F[Unit] = dummy
  override def log(span: Unit, fields: Seq[(String, TracingValue)]): F[Unit] = dummy
  override def setBaggageItem(span: Unit, key: String, value: String): F[Unit] = dummy
  override def getBaggageItem(span: Unit, key: String): F[Option[String]] = F.pure(None)
}
