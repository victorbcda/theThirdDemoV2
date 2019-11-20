package front

import akka.actor
import akka.actor.Props
import com.corundumstudio.socketio.{AckRequest, SocketIOClient}
import com.corundumstudio.socketio.listener.DataListener
import messageType.{Setup, StartedGame}
import play.api.libs.json.{JsValue, Json}

class RegisterListener(server: register) extends DataListener[String] {
  override def onData(socket: SocketIOClient, username: String, ackRequest: AckRequest): Unit = {
    println(username + " registered in the chat with socket " + socket)
    val parsed: JsValue = Json.parse(username)
    val userName:String=(parsed\"username").as[String]
    val password:String=(parsed\"password").as[String]
    server.database ! StartedGame(userName,password)
  }
}
