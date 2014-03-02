package services

import repositories.UserRepositoryComponent
import models.User
import play.api.db.slick.Profile
import scala.slick.driver.JdbcDriver

trait UserServiceComponent { this: UserRepositoryComponent =>
  def userService: UserService = new UserService {}

  trait UserService {
    def existsWithEmail(email: String): Boolean = db withSession { implicit session =>
      userRepository.existsWithEmail(email)
    }

    def authenticate(email: String, password: String): Option[User] = db withSession { implicit session =>
      userRepository authenticate (email, password)
    }

    def allUsers(): List[User] = db withSession (implicit session => userRepository.all())

    def insert(user: User): User = db withSession { implicit session =>
      userRepository.insert(user)
    }
  }
}

trait ProductionUserServiceComponent extends UserServiceComponent with UserRepositoryComponent with Profile {
  override lazy val db = play.api.db.slick.DB(play.api.Play.current)
  override lazy val profile: JdbcDriver = db.driver
}
