package com.prodtector.protocol.config

import upickle.default.{ReadWriter, macroRW}

final case class Screen(columns: Int, rows: Int, tiles: List[Tile]) {
  require(columns <= 10, "column must be <= 10")
  require(rows <= 10, "row must be <= 10")
}

object Screen {
  implicit val rw: ReadWriter[Screen] = macroRW
}