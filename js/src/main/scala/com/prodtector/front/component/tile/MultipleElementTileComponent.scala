package com.prodtector.front.component.tile

import com.prodtector.front.component.Component
import com.prodtector.protocol.config.model.Tile
import com.prodtector.protocol.config.model.Tile.MultipleElementTile
import com.raquo.laminar.api.L.*
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

object MultipleElementTileComponent extends Component[MultipleElementTile] {
  def apply(tile: MultipleElementTile): Element = {
    build(tile)
  }

  override def build(tile: MultipleElementTile, modifiers: Modifier[ReactiveHtmlElement[HTMLDivElement]]*): Element = {
    TileComponent.build(
      tile,
      cls := "title-multiple-element",
      tile.title
    )
  }
}
