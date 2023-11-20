package com.prodtector.protocol.config.model

//import upickle.default.{ReadWriter, macroRW}

final case class Screen(column: Int, row: Int /*,tile: List[Tile]*/)

//object Screen {
//  implicit val rw: ReadWriter[Screen] = macroRW
//}