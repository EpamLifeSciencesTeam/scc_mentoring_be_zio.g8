package $organization;format="space,lower,package"$.$name;format="camel"$.api

import $organization;format="space,lower,package"$.$name;format="camel"$.domain.uptime._
import $organization;format="space,lower,package"$.$name;format="camel"$.repository._
import io.circe.Encoder
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import zio._
import zio.interop.catz._

import java.lang.management.ManagementFactory

object UptimeApi {
  def routes[R <: Has[Repository]]: HttpRoutes[RIO[R, *]] = {
    type Task[A] = RIO[R, A]

    val dsl: Http4sDsl[Task] = Http4sDsl[Task]
    import dsl._

    implicit def circeJsonEncoder[A: Encoder]: EntityEncoder[Task, A] = jsonEncoderOf[Task, A]

    HttpRoutes.of[Task] {
      case GET -> Root =>
        Ok(Repository.getUptime.map { now =>
          val uptime = ManagementFactory.getRuntimeMXBean().getUptime()
          Uptime(now, uptime, "ok")
        })
    }
  }
}
