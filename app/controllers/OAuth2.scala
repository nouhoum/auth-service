package controllers

import play.api.mvc.{ Action, Controller }
import scalaoauth2.provider.OAuth2Provider
import services.OAuthService

trait OAuth2 { this: OAuth2Provider with Controller =>
  def accessToken = Action { implicit request =>
    issueAccessToken(new OAuthService)
  }

  def refresh = TODO
  def revoke = TODO
}

object OAuth2 extends OAuth2 with OAuth2Provider with Controller
