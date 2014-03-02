package services

import scalaoauth2.provider.DataHandler
import models.User

trait OAuthService extends DataHandler[User] {

}
