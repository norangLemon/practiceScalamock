package io.norang

final class User(id: User.Id, assets: Assets)

object User {
  type Id = String
}

final case class Assets(money: Assets.Money, heart: Assets.Heart)

object Assets {
  type Money = Int
  type Heart = Int
}

trait UserDb {
  def searchUserDataById: User.Id => SearchUserDataResult
}

sealed trait SearchUserDataResult
case object FailToSearchUserData extends SearchUserDataResult
case class SuccessToSearchUserData(user: User) extends SearchUserDataResult


class Game (db: UserDb) {
  def login: User.Id => LoginResult = id =>
    (db.searchUserDataById(id)) match {
      case FailToSearchUserData => LoginFail
      case SuccessToSearchUserData(user) => LoginSuccess(user)
  }
}

sealed trait LoginResult
case object LoginFail extends LoginResult
case class LoginSuccess(user: User) extends LoginResult
