package $organization;format="space,lower,package"$.$name;format="camel"$.domain.uptime

import io.circe.Encoder
import io.circe.generic.semiauto._

import java.time.LocalDateTime

final case class Uptime(now: LocalDateTime, uptime: Long, status: String)

object Uptime {
  implicit val encoder: Encoder[Uptime] = deriveEncoder
}
