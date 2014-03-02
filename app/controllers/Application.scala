package controllers

import play.api._
import play.api.mvc._
import scala.concurrent.Future
import services.{ UserServiceComponent, ProductionUserServiceComponent }

trait Application { this: Controller with UserServiceComponent =>
  def index = Action {
    Ok(views.html.index("OAuth 2 server"))
  }

  def allUsers = Action.async { implicit request =>

    Future.successful(Ok(views.html.users(userService.allUsers())))
  }
}

object Application extends Application with Controller with ProductionUserServiceComponent