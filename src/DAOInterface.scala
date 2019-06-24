package kakuroService

import org.bson.types.ObjectId
trait DAOInterface {
  //def getGridById(id: Int): (Int, String)
  //def getAllGrids: List[(Int, String)]
  //Create
  
  // Read
  def getGridById(id: ObjectId): (String)


  // Update


  // Delete
  //def saveGrid(grid: String): Unit

  //def deleteGridById(id: Int): Boolean
}