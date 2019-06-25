package kakuroService
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn
import org.mongodb.scala.bson.ObjectId
// Import not necessary because they are in the same package
//import kakuroService.databaseService

// Save Endpoint

// Load endpoint

// Init endpoint
object KakuroService {
     def main(args: Array[String]): Unit = {
         
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher


    val route =
      path("api" / "kakuro" / "fields" / "file") {
        get {
          val couch = new couchdbService()
          val field = couch.getFieldfromFile()
          complete(HttpEntity(ContentTypes.`application/json`, field.toString()))
        }
      }~
        // Read
        get { 
          pathPrefix("api" / "kakuro" / "fields" / IntNumber) { id => 
            val mongo = new mongodbService()
            val field = mongo.getGridById(id)
            complete(HttpEntity(ContentTypes.`application/json`,field.toString()))
          }
        
      }~ path("api" / "kakuro" / "fields") {
        // Create
        post {
          entity(as[String]) { grid =>
            val mongo = new mongodbService()
            val dbResp = mongo.saveGrid(grid)
            complete(HttpEntity(ContentTypes.`application/json`, "{'success': 'true'}"))
          }
          
        }
      }~ 
        // Update
        put {
          pathPrefix("api" / "kakuro" / "fields" / IntNumber) { id => 
            entity(as[String]) { grid =>
              val mongo = new mongodbService()
              
              val dbResp = mongo.editGrid(id,grid)
              complete(HttpEntity(ContentTypes.`application/json`, "{'success': 'true'}"))
            }
          }
          
          
        }
      

    
    // When running in docker do not use localhost because of the nating!
    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
     
    } 
}