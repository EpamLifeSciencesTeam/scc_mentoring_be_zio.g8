package $organization;format="space,lower,package"$.$name;format="camel"$.config

import pureconfig._
import pureconfig.generic.semiauto._
import zio._

final case class AppConfig(api: ApiConfig, db: DbConfig)

object AppConfig {
  implicit val convert: ConfigConvert[AppConfig] = deriveConvert

  val live: ZLayer[Any, IllegalStateException, Has[AppConfig]] =
    ZLayer.fromEffect {
      ZIO
        .fromEither(ConfigSource.default.load[AppConfig])
        .mapError(failures => new IllegalStateException(s"Error loading configuration: \$failures"))
    }

  val getAppConfig: URIO[Has[AppConfig], AppConfig] = ZIO.access(_.get)
}
