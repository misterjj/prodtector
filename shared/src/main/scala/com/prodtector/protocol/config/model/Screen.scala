package com.prodtector.protocol.config.model

import upickle.default.{ReadWriter, macroRW}

final case class Screen(column: Int, row: Int, tiles: List[Tile]) {
  require(column <= 10, "column must be <= 10")
  require(row <= 10, "row must be <= 10")
}

object Screen {
  implicit val rw: ReadWriter[Screen] = macroRW
}