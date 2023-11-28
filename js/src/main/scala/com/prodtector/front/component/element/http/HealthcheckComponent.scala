package com.prodtector.front.component.element.http

import com.prodtector.front.Tool.post
import com.prodtector.protocol.config.Tile
import com.prodtector.protocol.config.Tile.Element.Healthcheck
import com.prodtector.protocol.service.ServiceResponse
import com.prodtector.protocol.service.http.HealthcheckRequest
import com.raquo.laminar.api.L.*
import com.raquo.laminar.modifiers.Modifier
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import org.scalajs.dom.{HTMLDivElement, HttpMethod, RequestMode}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

final case class HealthcheckComponent(tile: Healthcheck)(implicit ec: ExecutionContext) {

  private def dataCollector(value: Var[ServiceResponse]): Unit = {
    val body = HealthcheckRequest(tile.url, tile.expectedResultCode)
    val f = post[HealthcheckRequest, ServiceResponse]("/service/http/healthcheck", body, RequestMode.`no-cors`)

    f.onComplete {
      case Failure(exception) => sys.error(exception.getMessage)
      case Success(response) => value.update(_ => response)
    }
  }

  def single(): Element = {
    val intVar: Var[ServiceResponse] = Var(ServiceResponse(false, ""))
    val intSignal: Signal[ServiceResponse] = intVar.signal

    //    js.timers.setInterval(30000) {
    //      intVar.update(_ + 1)
    //    }

    dataCollector(intVar)

    div(
      "single",
      tile.title,
      tile.url,
      child.text <-- intSignal.map(_.success)
    )
  }

  def multiple(): Element = {
    div(
      "multiple",
      tile.title,
      tile.url,
      tile.expectedResultCode
    )
  }
}
