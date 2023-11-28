package com.prodtector.front.component.tile

import com.prodtector.front.component.Component
import com.prodtector.protocol.config.Tile
import com.prodtector.protocol.config.Tile.{MultipleElementTile, SingleElementTile}
import com.raquo.laminar.api.L.*
import com.raquo.laminar.modifiers.Modifier
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import org.scalajs.dom.HTMLDivElement

import scala.concurrent.ExecutionContext

class TileComponent(tile: Tile) extends Component{
  def build(modifiers: Modifier[ReactiveHtmlElement[HTMLDivElement]]*): Element = {
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

object TileComponent {
  def apply(tile: Tile)(implicit ec: ExecutionContext): Component = {
    tile match
      case t: SingleElementTile => SingleElementTileComponent(t, new TileComponent(tile))
      case t: MultipleElementTile => MultipleElementTileComponent(t, new TileComponent(tile) )
  }
}

