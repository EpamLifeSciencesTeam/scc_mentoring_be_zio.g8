package $organization;format="space,lower,package"$.$name;format="camel"$.config

import pureconfig._
import pureconfig.generic.semiauto._
import zio._

final case class ApiConfig(endpoint: String, port: Int)

object ApiConfig {
  implicit val convert: ConfigConvert[ApiConfig] = deriveConvert

  val fromAppConfig: ZLayer[Has[AppConfig], Nothing, Has[ApiConfig]] = ZLayer.fromService(_.api)

  val getApiConfig: URIO[Has[ApiConfig], ApiConfig] = ZIO.access(_.get)
}
