package $package$.http.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server
import akka.http.scaladsl.server.Directives
import spray.json.DefaultJsonProtocol

import $package$.services.PingService
import $package$.dto.PingResponse

object PingJsonSupport extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val pingResponseFormat = jsonFormat1(PingResponse)
}

class PingRouter(pingService: PingService) extends Directives {

  import PingJsonSupport._

  val routes: server.Route = {
    path("ping") {
      get {
        complete(pingService.ping)
      }
    }
  }
}

