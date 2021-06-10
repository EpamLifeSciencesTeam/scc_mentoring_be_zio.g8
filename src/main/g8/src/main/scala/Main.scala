package $organization;format="space,lower,package"$.$name;format="camel"$

import cats.effect._
import $organization;format="space,lower,package"$.$name;format="camel"$.api._
import $organization;format="space,lower,package"$.$name;format="camel"$.config._
import $organization;format="space,lower,package"$.$name;format="camel"$.repository._
import org.http4s._
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.CORS
import zio.clock.Clock
import zio.interop.catz._
import zio.{ExitCode => ZExitCode, _}

object Main extends App {
  type AppTask[A] = RIO[layers.AppEnv with Clock, A]

  override def run(args: List[String]): ZIO[ZEnv, Nothing, ZExitCode] = {
    val prog =
      for {
        cfg    <- AppConfig.getAppConfig
        _      <- logging.log.info(s"Starting with \$cfg")
        httpApp = Router[AppTask](
                    "/uptime" -> UptimeApi.routes
                  ).orNotFound
        _      <- runHttp(httpApp, cfg.api.port, cfg.api.endpoint)
      } yield ZExitCode.success

    prog
      .provideSomeLayer[ZEnv](Repository.withTracing(layers.live.appLayer))
      .orDie
  }

  def runHttp[R <: Clock](
    httpApp: HttpApp[RIO[R, *]],
    port: Int,
    endpoint: String
  ): ZIO[R, Throwable, Unit] = {
    type Task[A] = RIO[R, A]

    ZIO.runtime[R].flatMap { implicit rts =>
      BlazeServerBuilder
        .apply[Task](rts.platform.executor.asEC)
        .bindHttp(port, endpoint)
        .withHttpApp(CORS(httpApp))
        .serve
        .compile[Task, Task, ExitCode]
        .drain
    }
  }
}
