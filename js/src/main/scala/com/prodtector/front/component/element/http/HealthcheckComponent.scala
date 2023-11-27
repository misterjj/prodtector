package com.prodtector.front.component.element.http

import com.prodtector.front.component.Component
import com.prodtector.protocol.config.Tile
import com.prodtector.protocol.config.Tile.Element.Healthcheck
import com.raquo.laminar.api.L.*
import com.raquo.laminar.modifiers.Modifier
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

import scala.scalajs.js

final case class HealthcheckComponent(tile: Healthcheck) {
  def single(): Element = {
//    js.timers.setInterval(200) {
//      println("test")
//    }

    div(
      "single",
      tile.title,
      tile.url
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
