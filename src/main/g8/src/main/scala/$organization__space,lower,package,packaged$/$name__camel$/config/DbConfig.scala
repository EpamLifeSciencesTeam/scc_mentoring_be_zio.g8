package $organization;format="space,lower,package"$.$name;format="camel"$.config

import pureconfig._
import pureconfig.generic.semiauto._
import zio._

final case class DbConfig(url: String, user: String, password: String, driver: String)

object DbConfig {
  implicit val convert: ConfigConvert[DbConfig] = deriveConvert

  val fromAppConfig: ZLayer[Has[AppConfig], Nothing, Has[DbConfig]] = ZLayer.fromService(_.db)

  val getDbConfig: URIO[Has[DbConfig], DbConfig] = ZIO.access(_.get)
}
