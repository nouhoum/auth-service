package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import services.{ UserServiceComponent, ProductionUserServiceComponent }

trait SignIn { this: Controller with UserServiceComponent =>
  def signin = Action { implicit request =>
    Ok(views.html.signin(loginForm))
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      errors => BadRequest(views.html.signin(errors)),
      credentials => Redirect(routes.Application.index).withSession("email" -> credentials._1)
    )
  }

  val loginForm = Form(
    tuple(
      "email" -> text, "password" -> text
    ).verifying("Email or password is wrong", credentials =>
        userService.authenticate(credentials._1, credentials._2).isDefined))
}

object SignIn extends SignIn with Controller with ProductionUserServiceComponent
