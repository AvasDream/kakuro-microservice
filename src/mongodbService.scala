package kakuroService
import java.io.FileInputStream
import scala.io.Source
import play.api.libs.json._
import scala.concurrent.Future
import org.mongodb.scala._

class mongodbService() {
    val client: MongoClient = MongoClient("mongodb://127.0.0.1:27017")
    println(client)
    val database: MongoDatabase = client.getDatabase("kakuro")
    println(database)
    val collection: MongoCollection[Document] = database.getCollection("fields_easy")
    println(collection)
    
    def getField(): String = {
        val field = collection.find().subscribe(new Observer[String] {
            override def onNext(result: Completed): String = {return result.toString()}
            override def onError(e: Throwable): String = {return "onError: $e"}
            override def onComplete(): String = {return "onComplete"}
        })
    return field
    }
}