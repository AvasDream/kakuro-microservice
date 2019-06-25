package kakuroService
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn
import org.mongodb.scala.bson.ObjectId

object KakuroService {
     def main(args: Array[String]): Unit = {
         
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher


    val route =
        // Create 
        path("api" / "kakuro" / "fields") {
        post {
          entity(as[String]) { grid =>
            val mongo = new mongodbService()
            val dbResp = mongo.saveGrid(grid)
            complete(HttpEntity(ContentTypes.`application/json`, dbResp))
          }
          
        }
      }~
        // Read
        get { 
          pathPrefix("api" / "kakuro" / "fields" / IntNumber) { id => 
            val mongo = new mongodbService()
            val field = mongo.getGridById(id)
            complete(HttpEntity(ContentTypes.`application/json`,field.toString()))
          }
      }~ 
        // Update
        put {
          pathPrefix("api" / "kakuro" / "fields" / IntNumber) { id => 
            entity(as[String]) { grid =>
              val mongo = new mongodbService()
              println(grid)
              val dbResp = mongo.editGrid(id,grid)
              complete(HttpEntity(ContentTypes.`application/json`, dbResp))
            }
          }
      }~
        //Delete
        delete {
          pathPrefix("api" / "kakuro" / "fields" / IntNumber) { id => 
            val mongo = new mongodbService()
            val dbResp = mongo.deleteGridById(id)
            complete(HttpEntity(ContentTypes.`application/json`,dbResp))
          }
        }
    // !!!! When running in docker do not use localhost because of the nating!
    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() 
    bindingFuture
      .flatMap(_.unbind()) 
      .onComplete(_ => system.terminate()) 
     
    } 
}