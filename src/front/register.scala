package front

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.corundumstudio.socketio.listener.DataListener
import com.corundumstudio.socketio.{AckRequest, Configuration, SocketIOClient, SocketIOServer}
import messageType.{GameState, Save, SaveGames, Update, UpdateGames}
import play.api.libs.json.{JsValue, Json}

class register (val database:ActorRef)extends Actor{
  var playerGameState: String = ""
  var usernameAndPassword:Map[String,String]=Map()
  var passwordAndUsername:Map[String,String]=Map()
  val config: Configuration = new Configuration {
    setHostname("localhost")
    setPort(8080)
  }
  val server: SocketIOServer = new SocketIOServer(config)
  server.addEventListener("register", classOf[String], new RegisterListener(this))
  server.start()




  override def receive: Receive = {
    case GameState(gameState:String)=>
      this.playerGameState=gameState
  }
}
