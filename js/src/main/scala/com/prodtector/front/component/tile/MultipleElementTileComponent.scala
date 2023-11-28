package com.prodtector.front.component.tile

import com.prodtector.front.component.Component
import com.prodtector.front.component.element.http.HealthcheckComponent
import com.prodtector.protocol.config.Tile
import com.prodtector.protocol.config.Tile.Element.*
import com.prodtector.protocol.config.Tile.MultipleElementTile
import com.raquo.laminar.api.L.*
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

import scala.concurrent.ExecutionContext

final case class MultipleElementTileComponent(tile: MultipleElementTile, component: TileComponent, elements: List[Element]) extends Component {
  override def build(modifiers: Modifier[ReactiveHtmlElement[HTMLDivElement]]*): Element = {
    component.build(
      cls := "title-multiple-element",
      tile.title,
      elements,
      modifiers
    )
  }
}


object MultipleElementTileComponent {
  def apply(tile: MultipleElementTile, component: TileComponent)(implicit ec: ExecutionContext): Component = {
    val elements = tile.elements.map {
      case el: Healthcheck => HealthcheckComponent(el).multiple()
    }

    new MultipleElementTileComponent(tile, component, elements)
  }
}
