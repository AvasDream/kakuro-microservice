package kakuroService
trait DAOInterface {
  //def getGridById(id: Int): (Int, String)
  //def getAllGrids: List[(Int, String)]
  def getGridById(): (Int, String)

  //def saveGrid(grid: String): Unit

  //def deleteGridById(id: Int): Boolean
}