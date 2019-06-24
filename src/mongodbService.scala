package kakuroService
import java.io.FileInputStream
import scala.io.Source
import play.api.libs.json._
import scala.concurrent.Future


import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson._
import org.mongodb.scala.model.Filters._
//ToDo only import what you really use
import org.mongodb.scala._
import org.bson.types.ObjectId
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class mongodbService() extends DAOInterface {

    val DEBUG = true
    // Class to BSON
    val codecRegistry = fromRegistries(fromProviders(classOf[Grid]), DEFAULT_CODEC_REGISTRY )
    
    val client: MongoClient = MongoClient("mongodb://user:password@ds343217.mlab.com:43217/kakuro")
    if (DEBUG) {println(client)}
    
    val database: MongoDatabase = client.getDatabase("kakuro").withCodecRegistry(codecRegistry)
    if (DEBUG) {println(database)}
    
    val collection: MongoCollection[Grid] = database.getCollection("fields_easy")
    if (DEBUG) {println(collection)}
    

    /*
    Field easy ID:
    5d09270e3a17ea2b9b975af1
    Hard:
    5d1105509feb476770941e68
    Medium:
    5d1105579feb476770941e6d
    */
    override def getGridById(id: ObjectId): (String) = {
        val data = Await.result(collection.find(equal("_id",id)).first().toFuture(),Duration.Inf).get()
        val field = data._2
        println(field)
        field
    }
}
case class Grid(_id: ObjectId, grid: String) {
  def get(): (ObjectId,String) = (_id,grid)
}