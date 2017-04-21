package $package$

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http

import com.typesafe.config.ConfigFactory

import $package$.http.Router
import $package$.services.PingService


object Main {

  private val config = ConfigFactory.load()

  private val httpPort = config.getInt("http.port")
  private val httpHost = config.getString("http.host")

  def main(args: Array[String]): Unit = {

    // init akka
    implicit val actorSystem = ActorSystem("places_service")
    implicit val executor = actorSystem.dispatcher
    implicit val materializer: ActorMaterializer = ActorMaterializer()

    val router = new Router(new PingService())
    Http().bindAndHandle(router.routes, httpHost, httpPort)
    println(s"server started at \$httpPort:\$httpHost")
  }
}
