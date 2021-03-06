package kakuroService
import java.io.FileInputStream
import scala.io.Source
import play.api.libs.json._
import scala.concurrent.Future


import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.model.Filters._
//ToDo only import what you really use
import org.mongodb.scala._
import org.bson.types.ObjectId
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class mongodbService() extends DAOInterface {

    val DEBUG = false
    // Class to BSON
    val codecRegistry = fromRegistries(fromProviders(classOf[Grid]), DEFAULT_CODEC_REGISTRY )
    // !!!! Access docker host from docker container with HOST ip address !!!!
    val client: MongoClient = MongoClient("mongodb://192.168.178.21:27017")
    if (DEBUG) {println(client)}
    
    val database: MongoDatabase = client.getDatabase("kakuro").withCodecRegistry(codecRegistry)
    if (DEBUG) {println(database)}
    
    val collection: MongoCollection[Grid] = database.getCollection("fields_test")
    if (DEBUG) {println(collection)}
    
    // Read
    override def getGridById(id: Int): (String) = {
        try {
            val res = collection.find(equal("_id",id)).first().toFuture()
            val grid = Await.result(res,Duration.Inf)
            val data = grid.get()
            val field = data._2
            field
        } catch {
            case _: Throwable => "{'success':'false'}" 
        }
        
    }
    // Create
    override def saveGrid(grid: String): String = {
    /* 
    !!! count is of type Int, Possible part for problems when doing stress tests with a lot of inserts
    !!! Database acces for collection count, could be optimized for performance
    */
    try
    {
        val count = Await.result(collection.count().toFuture(), Duration.Inf)
        println("Elements in Collection:" + count)
        val inc = count.toInt + 1
        val res = collection.insertOne(new Grid(inc, grid)).toFuture() 
        Await.result(res, Duration.Inf)
        "{'success':'true'}"
    } catch {
        case _: Throwable => "{'success':'false'}"
        }
    }
    // Update
    override def editGrid(id:Int, grid:String): String = {
        try {
            val res = collection.replaceOne(equal("_id", id), new Grid(id,grid)).toFuture()
            Await.result(res,Duration.Inf)
            "{'success':'true'}"
        } catch {
            case _: Throwable => "{'success':'false'}"
        }
        
    }
    // Delete
    override def deleteGridById(id: Int): String = {
        val res = collection.deleteOne(equal("_id", id)).toFuture()
        Await.result(res,Duration.Inf)
        "{'success':'true'}"
    }
}
case class Grid(_id: Int, grid: String) {
  def get(): (Int,String) = (_id,grid)
}