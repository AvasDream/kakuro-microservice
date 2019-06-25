package kakuroService
import slick.driver.H2Driver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class slickService extends DAOInterface {
    private val fields = TableQuery[fields_test]
    val db = Database.forConfig("h2mem1")
    // Create the tables everytime because didnt configure docker persistence
    // Performance hell but we dont use slick in prod
    db.run(DBIO.seq(fields.schema.create))
    // Create
    def saveGrid(grid: String): String = {
        try
        {
            val query = db.run(fields += (0, grid))
            Await.result(query, Duration.Inf)
            "{'success':'true'}"
            
        } catch {
            case _: Throwable => "{'success':'false'}"
        }
    }
    // Read
    def getGridById(id: Int): String = {
        try
        {
            val query = db.run(fields.filter(_.id === id).result.headOption)
            Await.result(query, Duration.Inf).get
            "{'success':'true'}"
            
        } catch {
            case _: Throwable => "{'success':'false'}"
        }
    }
    // Update
    def editGrid(id: Int,grid: String): String = {
        try
        {
            db.run(fields.filter(_.id === id) += (0,grid))
            "{'success':'true'}"
        } catch {
            case _: Throwable => "{'success':'false'}"
        }
    }
    // Delete
    def deleteGridById(id: Int): String = {
        try
        {
            val query = db.run(fields.filter(_.id === id).delete) map { _ > 0}
            Await.result(query, Duration.Inf)
            "{'success':'true'}" 
        } catch {
            case _: Throwable => "{'success':'false'}"
        }
    }
}


case class fields_test(tag: Tag) extends Table[(Int, String)](tag, "fields_test") {
  def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def grid: Rep[String] = column[String]("grid")
  def * = (id,grid)
}