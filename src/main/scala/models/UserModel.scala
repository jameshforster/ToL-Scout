package models

import org.spongepowered.api.entity.living.player.Player
import common.Mappers._

case class UserModel(playerName: String)

object UserModel {
  implicit val convert: Player => UserModel = player => UserModel(player.getName)
  implicit val listConvert: List[Player] => List[UserModel] = list => list.map(convert)
  implicit val listFormat: List[UserModel] => String = list => jsonMapper.writeValueAsString(list)
}
