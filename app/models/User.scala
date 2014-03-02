package models

import play.api.db.DB
import play.api.Play.current

case class User(id: Option[Long], email: String, password: String, name: Option[String])

object Users {
  import scala.slick.driver.H2Driver.simple._

  val db = Database.forDataSource(DB.getDataSource())

  class Users(tag: Tag) extends Table[User](tag, "USERS") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def email = column[String]("email", O.NotNull)
    def password = column[String]("password", O.NotNull)
    def name = column[String]("name")

    def * = (id.?, name, password, name.?) <> (User.tupled, User.unapply)
  }

  val users = TableQuery[Users]

  def existsWithEmail(email: String): Boolean = db.withSession { implicit session =>
    val q = users.filter(_.email === email)
    q.firstOption.isDefined
  }

  def insert(user: User): User = db.withSession { implicit session =>
    autoInc.insert(user)
  }

  private val autoInc = users returning users.map(_.id) into {
    case (u, id) => u.copy(id = Option(id))
  }
}
