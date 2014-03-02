package forms

case class UserForm(email: String, password: String, name: Option[String])