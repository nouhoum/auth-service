package repositories

import play.api.db.slick.Profile
import models.User
import scala.slick.driver.JdbcDriver

trait UsersRepositoryComponent { this: Profile =>
  import profile.simple._

  def db: Database

  class Users(tag: Tag) extends Table[User](tag, "USERS") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def email = column[String]("email", O.NotNull)
    def password = column[String]("password", O.NotNull)
    def name = column[String]("name")

    def * = (id.?, email, password, name.?) <> (User.tupled, User.unapply)
  }

  val users = TableQuery[Users]

  def usersRepository: UsersRepository = new UsersRepository {}

  trait UsersRepository {
    def existsWithEmail(email: String)(implicit session: Session): Boolean =
      users.filter(_.email === email).exists.run

    def authenticate(email: String, password: String)(implicit session: Session): Option[User] =
      users.filter(user => user.email === email && user.password === password).firstOption

    def all()(implicit session: Session): List[User] =
      users.list

    def insert(user: User)(implicit session: Session): User = autoInc.insert(user)

    private val autoInc = users returning users.map(_.id) into {
      case (u, id) => u.copy(id = Option(id))
    }
  }
}

trait ProductionUsersRepositoryComponent extends UsersRepositoryComponent with Profile {
  override lazy val db = play.api.db.slick.DB(play.api.Play.current)
  lazy val profile: JdbcDriver = db.driver
}
