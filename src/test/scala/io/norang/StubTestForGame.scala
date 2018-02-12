package io.norang

import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec
import io.norang.User.Id

class StubTestForGame extends FlatSpec with MockFactory {
  val invalidUserId:User.Id = "not-exist"

  class UserDbFacade extends UserDb {
    override def searchUserDataById(id: Id): SearchUserDataResult = FailToSearchUserData
  }

  "UserDb" should "fail to search User if the user is not exist in DB" in {
    val userDbFacadeStub = stub[UserDbFacade]

   (userDbFacadeStub.searchUserDataById (_:User.Id)).when(invalidUserId).returns(FailToSearchUserData)

    val game = new Game(userDbFacadeStub)
    (game.login _).verify(invalidUserId)
  }

}
