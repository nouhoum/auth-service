package controllers

import play.api.mvc.{ Action, Controller }
import play.api.data._
import play.api.data.Forms._
import repositories.{ ProductionUsersRepositoryComponent, UsersRepositoryComponent }
import models.User

trait SignUp { this: Controller with UsersRepositoryComponent =>
  def signup = Action { implicit request =>
    Ok(views.html.signup(form))
  }

  def create = Action { implicit request =>
    form.bindFromRequest.fold(
      errors =>
        BadRequest(views.html.signup(errors)),
      user => {
        db.withSession { implicit session =>
          usersRepository.insert(user)
        }
        Redirect(routes.Application.index)
      }
    )
  }

  val form: Form[User] = Form(
    mapping(
      "id" -> optional(longNumber),
      "email" -> email.verifying("A user with this email exists",
        email => !db.withSession(implicit session => usersRepository.existsWithEmail(email))
      ),
      "password" -> text(minLength = 4),
      "name" -> optional(text)
    )(User.apply)(User.unapply))
}

object SignUp extends SignUp with Controller with ProductionUsersRepositoryComponent
