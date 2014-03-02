package repositories

import play.api.db.slick.{ Profile, Database }
import scala.slick.driver.JdbcProfile

case class Ddl(override val profile: JdbcProfile, override val db: Database) extends UsersRepositoryComponent with Profile {
  import profile.simple._

  def create(): Unit = {
    db withSession { implicit session =>
      users.ddl.create
    }
  }

  def drop(): Unit = {
    db withSession { implicit session =>
      users.ddl.drop
    }
  }
}
