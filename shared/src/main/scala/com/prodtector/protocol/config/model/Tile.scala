package com.prodtector.protocol.config.model

import upickle.default.{ReadWriter, macroRW}

sealed trait Tile {
  val width: Int
  val height: Int
  val x: Int
  val y: Int
}

object Tile {
  implicit val rw: ReadWriter[Tile] = ReadWriter.merge(SingleElementTile.rw, MultipleElementTile.rw)

  @upickle.implicits.key("SINGLE") final case class SingleElementTile(width: Int, height: Int, x: Int, y: Int, element: Element) extends Tile

  object SingleElementTile {
    implicit val rw: ReadWriter[SingleElementTile] = macroRW
  }

  @upickle.implicits.key("MULTIPLE") final case class MultipleElementTile(width: Int, height: Int, x: Int, y: Int, elements: List[Element]) extends Tile

  object MultipleElementTile {
    implicit val rw: ReadWriter[MultipleElementTile] = macroRW
  }

  sealed trait Element {
    val title: String
  }

  object Element {
    implicit val rw: ReadWriter[Element] = ReadWriter.merge(Healthcheck.rw)

    @upickle.implicits.key("HTTP_HEALTHCHECK") final case class Healthcheck(title: String) extends Element

    object Healthcheck {
      implicit val rw: ReadWriter[Healthcheck] = macroRW
    }
  }

}