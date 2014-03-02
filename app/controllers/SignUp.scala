package controllers

import play.api.mvc.{ Action, Controller }
import play.api.data._
import play.api.data.Forms._
import models.User
import services.{ ProductionUserServiceComponent, UserServiceComponent }

trait SignUp { this: Controller with UserServiceComponent =>
  def signup = Action { implicit request =>
    Ok(views.html.signup(form))
  }

  def create = Action { implicit request =>
    form.bindFromRequest.fold(
      errors =>
        BadRequest(views.html.signup(errors)),
      user => {
        userService.insert(user)
        Redirect(routes.Application.index)
      }
    )
  }

  val form: Form[User] = Form(
    mapping(
      "id" -> optional(longNumber),
      "email" -> email.verifying("A user with this email exists",
        email => !userService.existsWithEmail(email)
      ),
      "password" -> text(minLength = 4),
      "name" -> optional(text)
    )(User.apply)(User.unapply))
}

object SignUp extends SignUp with Controller with ProductionUserServiceComponent
