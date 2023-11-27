package com.prodtector.protocol.service

import upickle.default.{ReadWriter, macroRW}

final case class ServiceResponse(success: Boolean, response: String)

object ServiceResponse {
  implicit val rw: ReadWriter[ServiceResponse] = macroRW
}