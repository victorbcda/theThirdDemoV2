package database

trait Database {

  def playerExists(username: String): Boolean
  def createPlayer(username: String,password:String): Unit
  def saveGameState(username: String, gameState: String): Unit
  def loadGameState(username: String): String
  def returnUsername(password:String):String
  def passwordExists(password:String):Boolean

}
