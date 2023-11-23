package com.prodtector.front.component.tile

import com.prodtector.front.component.Component
import com.prodtector.protocol.config.model.Tile
import com.prodtector.protocol.config.model.Tile.SingleElementTile
import com.raquo.laminar.api.L.*
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

object SingleElementTileComponent extends Component[SingleElementTile] {
  def apply(tile: SingleElementTile): Element = {
    build(tile)
  }

  override def build(tile: SingleElementTile, modifiers: Modifier[ReactiveHtmlElement[HTMLDivElement]]*): Element = {
    TileComponent.build(
      tile,
      cls := "title-single-element",
      tile.element.title,
      "fdsfdsf2"
    )
  }
}
