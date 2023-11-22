package com.prodtector.front.component.tile

import com.prodtector.protocol.config.model.Tile
import com.prodtector.protocol.config.model.Tile.SingleElementTile
import com.raquo.laminar.api.L.*

object SingleElementTileComponent {
  def apply(tile: SingleElementTile): Element = {
    div(
      cls := "tile title-single-element",
      cls := s"x-${tile.x}",
      cls := s"y-${tile.x}",
      cls := s"w-${tile.width}",
      cls := s"h-${tile.height}",
      tile.element.title
    )
  }
}
