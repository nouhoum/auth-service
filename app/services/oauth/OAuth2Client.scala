package services.oauth

case class OAuth2Client(id: String, secret: String, name: String)

object OAuth2Clients {
  private lazy val clients = Seq(
    OAuth2Client("test-client-id", "test-client-secret", "Test OAuth Client")
  )

  def get(id: String): Option[OAuth2Client] = clients.find(_.id == id)
}