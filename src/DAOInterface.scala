package kakuroService

import org.bson.types.ObjectId
trait DAOInterface {
  //Create
  def saveGrid(grid: String): String
  // Read
  def getGridById(id: Int): (String)
  // Update
  def editGrid(id:Int, grid:String): String
  // Delete
  def deleteGridById(id: Int): String
}