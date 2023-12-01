package com.prodtector.protocol.config

import upickle.default
import upickle.default.ReadWriter
import upickle.default.macroRW

sealed trait Tile {
  val width: Int
  val height: Int
  val row: Int
  val col: Int
}

object Tile {
  implicit val rw: ReadWriter[Tile] = ReadWriter.merge(SingleElementTile.rw, MultipleElementTile.rw)

  object SingleElementTile {
    implicit val rw: ReadWriter[SingleElementTile] = macroRW
  }

  @upickle.implicits.key("SINGLE")
  final case class SingleElementTile(width: Int, height: Int, row: Int, col: Int, element: Element) extends Tile

  object MultipleElementTile {
    implicit val rw: ReadWriter[MultipleElementTile] = macroRW
  }

  trait Serializable[T] {
    implicit val rw: ReadWriter[T]
  }

  @upickle.implicits.key("MULTIPLE")
  final case class MultipleElementTile(
      width: Int,
      height: Int,
      row: Int,
      col: Int,
      title: String,
      elements: List[Element]
  ) extends Tile

  sealed trait Element {
    val title: String
    val delay: Int
  }

  object Element {
    implicit val rw: ReadWriter[Element] = ReadWriter.merge(Healthcheck.rw)

    @upickle.implicits.key("HTTP_HEALTHCHECK")
    final case class Healthcheck(title: String, delay: Int = 10000, url: String, expectedResultCode: Int)
        extends Element

    object Healthcheck extends Serializable[Healthcheck] {
      override implicit val rw: ReadWriter[Healthcheck] = macroRW
    }
  }
}
