package kakuroService
import java.io.FileInputStream
import scala.io.Source
import play.api.libs.json._
import scala.concurrent.Future
import com.ibm.couchdb._
// Fake it till you make it

class databaseService() {
    
    def getFieldfromFile(): JsValue = {
        val filename = "grid.json"
        val stream = new FileInputStream(filename)
        val json = try {  Json.parse(stream) } finally { stream.close() }
        return json
    }
    def getField(): String = {
        val couch = CouchDb("127.0.0.1", 5984)
        val kakuro_db = couch.db("kakuro", TypeMapping.empty)
        val test = kakuro_db.docs.getMany
        println(test.toString())
        return "Hello"
    }
}