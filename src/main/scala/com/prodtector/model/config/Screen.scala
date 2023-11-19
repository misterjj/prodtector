package com.prodtector.model.config

import upickle.default.macroRW
import upickle.default.ReadWriter

final case class Screen(column: Int, row: Int /*,tile: List[Tile]*/)

object Screen {
  implicit val rw: ReadWriter[Screen] = macroRW
}