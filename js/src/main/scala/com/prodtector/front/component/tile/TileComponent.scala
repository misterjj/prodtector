package com.prodtector.front.component.tile

import com.prodtector.protocol.config.model.Tile
import com.prodtector.protocol.config.model.Tile.{MultipleElementTile, SingleElementTile}
import com.raquo.laminar.api.L.*

object TileComponent {
  def apply(tile: Tile): Element = {
    tile match
      case t : SingleElementTile => SingleElementTileComponent(t)
      case t: MultipleElementTile => MultipleElementTileComponent(t)
  }
}
