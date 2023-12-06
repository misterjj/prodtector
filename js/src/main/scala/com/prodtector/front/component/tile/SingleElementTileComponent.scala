package com.prodtector.front.component.tile

import com.prodtector.front.component.Component
import com.prodtector.front.component.element.http.HealthcheckComponent
import com.prodtector.protocol.config.Tile
import com.prodtector.protocol.config.Tile.Element.*
import com.prodtector.protocol.config.Tile.SingleElementTile
import com.raquo.laminar.api.L.*
import com.raquo.laminar.modifiers.Modifier
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

import scala.concurrent.ExecutionContext

final case class SingleElementTileComponent(tile: SingleElementTile, component: TileComponent, children: Element)
    extends Component {
  override def build(modifiers: Modifier[ReactiveHtmlElement[HTMLDivElement]]*): Element = {
    component.build(
      cls := "title-single-element",
      children,
      modifiers
    )
  }
}

object SingleElementTileComponent {
  def apply(tile: SingleElementTile, component: TileComponent)(implicit
      ec: ExecutionContext
  ): SingleElementTileComponent = {
    val element = tile.element match {
      case el: Healthcheck => HealthcheckComponent(el).single()
    }

    new SingleElementTileComponent(tile, component, element)
  }

  def render(title: String, clazz: Signal[String], content: Element): Element = {
    div(
      cls := "element",
      cls <-- clazz,
      div(cls := "element-title", title),
      div(cls := "element-content vertical-align", content)
    )
  }
}
