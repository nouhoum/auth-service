package controllers

import play.api._
import play.api.mvc._
import repositories.{ ProductionUsersRepositoryComponent, UsersRepositoryComponent }
import scala.concurrent.Future

trait Application { this: Controller with UsersRepositoryComponent =>
  def index = Action {
    Ok(views.html.index("OAuth 2 server"))
  }

  def allUsers = Action.async { implicit request =>
    db.withSession { implicit session =>
      Future.successful(Ok(views.html.users(usersRepository.all())))
    }
  }
}

object Application extends Application with Controller with ProductionUsersRepositoryComponent