package $organization;format="space,lower,package"$.$name;format="camel"$.repository

import $organization;format="space,lower,package"$.$name;format="camel"$.config._
import cats.effect.Blocker
import doobie.hikari._
import zio._
import zio.blocking.Blocking
import zio.interop.catz._
import zio.logging.{log, Logging}

import java.time.LocalDateTime

trait Repository extends Serializable {
  def getUptime: UIO[LocalDateTime]
}

object Repository extends Serializable {
  def getUptime: URIO[Has[Repository], LocalDateTime] = ZIO.serviceWith[Repository](_.getUptime)

  def withTracing[RIn, ROut <: Has[Repository] with Logging, E](layer: ZLayer[RIn, E, ROut]): ZLayer[RIn, E, ROut] =
    layer >>> ZLayer.fromFunctionMany[ROut, ROut] { env =>
      def trace(call: => String) = log.trace(s"Repository.\$call")

      env.update[Repository] { service =>
        new Repository {
          val getUptime: UIO[LocalDateTime] = (trace("getUptime") *> service.getUptime).provide(env)
        }
      }
    }

  def mkTransactor(cfg: DbConfig): ZManaged[Blocking, Throwable, HikariTransactor[Task]] =
    ZIO.runtime[Blocking].toManaged_.flatMap { implicit rt =>
      for {
        transactEC <- Managed.succeed(rt.environment.get[Blocking.Service].blockingExecutor.asEC)
        transactor <- HikariTransactor
                        .newHikariTransactor[Task](
                          cfg.driver,
                          cfg.url,
                          cfg.user,
                          cfg.password,
                          rt.platform.executor.asEC,
                          Blocker.liftExecutionContext(transactEC)
                        )
                        .toManaged
      } yield transactor
    }
}
