package services

import scalaoauth2.provider.{ AuthInfo, AccessToken, DataHandler }
import models.User
import services.oauth.{ OAuth2Client, OAuth2Clients }

class OAuthService extends DataHandler[User] {
  def validateClient(clientId: String, clientSecret: String, grantType: String): Boolean =
    OAuth2Clients.get(clientId) match {
      case Some(OAuth2Client(id, secret, _)) if (secret == clientSecret) => true
      case _ => false
    }

  def findUser(username: String, password: String): Option[User] = ???

  def createAccessToken(authInfo: AuthInfo[User]): AccessToken = ???

  def getStoredAccessToken(authInfo: AuthInfo[User]): Option[AccessToken] = ???

  def refreshAccessToken(authInfo: AuthInfo[User], refreshToken: String): AccessToken = ???

  def findAuthInfoByCode(code: String): Option[AuthInfo[User]] = ???

  def findAuthInfoByRefreshToken(refreshToken: String): Option[AuthInfo[User]] = ???

  def findClientUser(clientId: String, clientSecret: String, scope: Option[String]): Option[User] = ???

  def findAccessToken(token: String): Option[AccessToken] = ???

  def findAuthInfoByAccessToken(accessToken: AccessToken): Option[AuthInfo[User]] = ???
}
