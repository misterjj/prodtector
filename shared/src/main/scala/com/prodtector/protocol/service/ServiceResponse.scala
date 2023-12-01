package com.prodtector.protocol.service

import com.prodtector.protocol.service.ServiceResponse.Status
import upickle.default.{ReadWriter, macroRW}

final case class ServiceResponse(status: Status, response: String)

object ServiceResponse {
  implicit val rw: ReadWriter[ServiceResponse] = macroRW

  enum Status derives ReadWriter:
    case SUCCESS, RUNNING, FAIL
}
