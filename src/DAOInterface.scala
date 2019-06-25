package kakuroService

import org.bson.types.ObjectId
trait DAOInterface {
  //def getGridById(id: Int): (Int, String)
  //def getAllGrids: List[(Int, String)]
  //Create
  def saveGrid(grid: String): Unit
  // Read
  def getGridById(id: Int): (String)
  // Update
  def editGrid(id:Int, grid:String): String

  // Delete
  //def saveGrid(grid: String): Unit

  //def deleteGridById(id: Int): Boolean
}