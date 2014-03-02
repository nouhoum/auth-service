package controllers

import play.api.mvc.Controller

trait OAuth { this: Controller =>
  def accessToken = TODO
  def refresh = TODO
  def revoke = TODO
}

object OAuth extends OAuth with Controller
