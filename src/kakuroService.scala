package kakuroService
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn
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

    val dbs = new databaseService()

    val route =
      path("api" / "kakuro" / "fields") {
        get {
          val field = dbs.getFieldfromFile()
          complete(HttpEntity(ContentTypes.`application/json`, field.toString()))
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