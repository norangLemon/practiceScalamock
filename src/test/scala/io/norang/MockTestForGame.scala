package io.norang

import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec
import io.norang.User.Id

class MockTestForGame extends FlatSpec with MockFactory {

  class UserDbFacade extends UserDb {
    override def searchUserDataById(id: Id): SearchUserDataResult = FailToSearchUserData
  }

  "UserDb" should "fail to search User if the user is not exist in DB" in {
    val userDbFacadeMock = mock[UserDbFacade]
    val invalidUserId = "not-exist"

    (userDbFacadeMock.searchUserDataById(_:User.Id)).expects(invalidUserId).returning(FailToSearchUserData)

    val game = new Game(userDbFacadeMock)
    game.login(invalidUserId)
  }

}
