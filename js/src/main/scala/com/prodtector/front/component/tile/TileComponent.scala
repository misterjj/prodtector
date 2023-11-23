package com.prodtector.front.component.tile

import com.prodtector.front.component.Component
import com.prodtector.protocol.config.model.Tile
import com.prodtector.protocol.config.model.Tile.{MultipleElementTile, SingleElementTile}
import com.raquo.laminar.api.L.*
import com.raquo.laminar.modifiers.Modifier
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import org.scalajs.dom.HTMLDivElement

object TileComponent extends Component[Tile] {
  def apply(tile: Tile): Element = {
    tile match
      case t: SingleElementTile => SingleElementTileComponent(t)
      case t: MultipleElementTile => MultipleElementTileComponent(t)
  }

  def build(tile: Tile, modifiers: Modifier[ReactiveHtmlElement[HTMLDivElement]]*): Element = {
    div(
      cls := "tile",
      cls := s"row-${tile.row}",
      cls := s"col-${tile.col}",
      cls := s"w-${tile.width}",
      cls := s"h-${tile.height}",
      modifiers
    )
  }
}

