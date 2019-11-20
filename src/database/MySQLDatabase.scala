package database
import java.sql.{Connection, DriverManager, ResultSet}
import scala.io.Source
class MySQLDatabase  extends Database {

  val url = "jdbc:mysql://localhost/mysql?serverTimezone=UTC"
  val username = "root"
  val password = "clickerSQL"
  var connection: Connection = DriverManager.getConnection(url, username, password)

  setupTable()

  def setupTable(): Unit = {
    val statement = connection.createStatement()
    statement.execute("CREATE TABLE IF NOT EXISTS players (username TEXT, password TEXT, gameState TEXT)")
  }


  def playerExists(username: String): Boolean = {
    val statement = connection.prepareStatement("SELECT * FROM players WHERE username=?")
    statement.setString(1, username)
    val result: ResultSet = statement.executeQuery()
    result.next()
  }

  def returnUsername(password:String):String={
    val statement = connection.prepareStatement("SELECT * FROM players WHERE password=?")
    statement.setString(1, password)
    val result: ResultSet = statement.executeQuery()
    result.next()
    result.getString("username")
  }

  def passwordExists(password:String):Boolean={
    val statement = connection.prepareStatement("SELECT * FROM players WHERE password=?")
    statement.setString(1, password)
    val result: ResultSet = statement.executeQuery()
    result.next()
  }


  def createPlayer(username: String,password:String): Unit = {
    val statement = connection.prepareStatement("INSERT INTO players VALUE (?, ?, ?)")
    statement.setString(1, username)
    statement.setString(2, password)
    val newGame: String = Source.fromFile("newGame.json").mkString
      .replace("USERNAME", username)
      .replace("TIMESTAMP", System.nanoTime().toString)
    statement.setString(2, newGame)
    statement.execute()
  }


  def saveGameState(username: String, gameState: String): Unit = {
    val statement = connection.prepareStatement("UPDATE players SET gameState = ? WHERE username = ?")
    statement.setString(1, gameState)
    statement.setString(2, username)
    statement.execute()
  }


  def loadGameState(username: String): String = {
    val statement = connection.prepareStatement("SELECT * FROM players WHERE username=?")
    statement.setString(1, username)
    val result: ResultSet = statement.executeQuery()
    result.next()
    result.getString("gameState")
  }

}
