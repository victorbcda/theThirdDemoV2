package database

import akka.actor.Actor
import messageType.{GameState, SaveGame, StartedGame}
class DatabaseActor(dbType: String) extends Actor {

  val database: Database =  new MySQLDatabase()

  override def receive: Receive = {
    case SaveGame(username: String, gameState: String)=>
      this.database.saveGameState(username,gameState)
    case StartedGame(username: String,password:String)=>
      if(this.database.playerExists(username)){
        sender() ! GameState(this.database.loadGameState(username))
      }
      else{
        this.database.createPlayer(username,password)
      }

  }

}
