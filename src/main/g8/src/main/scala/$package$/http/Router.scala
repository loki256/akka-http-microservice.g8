package $package$.http

import akka.http.scaladsl.server
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.ExceptionHandler
import akka.http.scaladsl.server.Directives._

import $package$.services.PingService
import $package$.http.routes.PingRouter


class Router(pingService: PingService) {

  val exceptionHandler = ExceptionHandler {
    case ex: Throwable =>
      complete(StatusCodes.BadRequest, s"\${ex.getMessage}")
  }

  val pingRouter = new PingRouter(pingService)

  val routes: server.Route =
    handleExceptions(exceptionHandler) {
      pathSingleSlash {
        get {
          complete("hello world")
        }
      } ~ pingRouter.routes
    }
}

