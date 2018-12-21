package puretracing.api

/**
  * Selected functionality from opentracing's Tracer as well as Span
  * Doesn't deal with context propagation
  */
trait Tracer[F[_]] {
  type Span

  def startRootSpan(operationName: String): F[Span]
  def startChild(span: Span, operationName: String): F[Span]
  def finish(span: Span): F[Unit]
  def setTag(span: Span, key: String, value: TracingValue): F[Unit]
  def log(span: Span, fields: Seq[(String, TracingValue)]): F[Unit]
  def setBaggageItem(span: Span, key: String, value: String): F[Unit]
  def getBaggageItem(span: Span, key: String): F[Option[String]]
}

object Tracer {
  def apply[F[_]](implicit ev: Tracer[F]): Tracer[F] = ev
}

/**
  * Deals with context propagation.
  * Low level, users should use cat's dsl
  */
trait Propagation[F[_]] extends Tracer[F] { // F could be ReaderT[IO]
  def currentSpan: F[Span]
  def useSpanIn[A](span: Span)(fa: F[A]): F[A]
}

object Propagation {
  def apply[F[_]](implicit ev: Propagation[F]): Propagation[F] = ev
}

sealed trait TracingValue // Poor man's union types
object TracingValue {
  final case class StringTracingValue(value: String) extends TracingValue
  final case class NumberTracingValue(value: Number) extends TracingValue
  final case class BooleanTracingValue(value: Boolean) extends TracingValue
}
