package kakuroService
import java.io.FileInputStream
import scala.io.Source
import play.api.libs.json._
import scala.concurrent.Future
// Fake it till you make it

class databaseService {
    def getField(): JsValue = {
        val filename = "grid.json"
        val stream = new FileInputStream(filename)
        val json = try {  Json.parse(stream) } finally { stream.close() }
        return json
    }
}