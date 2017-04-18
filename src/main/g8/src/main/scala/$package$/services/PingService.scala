package $package$.services

import $package$.dto.PingResponse

class PingService {
  def ping: PingResponse = PingResponse("pong")
}
