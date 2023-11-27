package com.prodtector.protocol.service.http

import upickle.default.{ReadWriter, macroRW}

final case class HealthcheckRequest(url: String, expectedResultCode: Int)

object HealthcheckRequest {
  implicit val rw: ReadWriter[HealthcheckRequest] = macroRW
}