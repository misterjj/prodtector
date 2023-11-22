package com.prodtector.front.component.tile

import com.prodtector.protocol.config.model.Tile
import com.prodtector.protocol.config.model.Tile.{MultipleElementTile, SingleElementTile}
import com.raquo.laminar.api.L.*

object MultipleElementTileComponent {
  def apply(tile: MultipleElementTile): Element = {
    div(
      cls := "tile title-multiple-element",
      cls := s"x-${tile.x}",
      cls := s"y-${tile.x}",
      cls := s"w-${tile.width}",
      cls := s"h-${tile.height}",
      tile.title
    )
  }
}
