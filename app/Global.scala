import play.api.GlobalSettings
import repositories.Ddl
import scala.slick.driver.JdbcDriver

object Global extends GlobalSettings {
  lazy val ddl = {
    lazy val db = play.api.db.slick.DB(play.api.Play.current)
    lazy val profile: JdbcDriver = db.driver
    Ddl(profile, db)
  }
  override def onStart(app: play.api.Application) {
    ddl.create()
  }

  override def onStop(app: play.api.Application) {
    ddl.drop()
  }
}
