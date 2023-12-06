package com.prodtector.protocol.config

import com.prodtector.protocol.config.Tile.Element.ElementRenderer
import com.prodtector.protocol.config.Tile.Element.ElementRenderer.ElementClassicRenderer
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
    val renderer: ElementRenderer
  }

  object Element {
    implicit val rw: ReadWriter[Element] = ReadWriter.merge(Healthcheck.rw)

    @upickle.implicits.key("HTTP_HEALTHCHECK")
    final case class Healthcheck(
        title: String,
        delay: Int = 10000,
        renderer: ElementRenderer = ElementClassicRenderer(),
        url: String,
        expectedResultCode: Int
    ) extends Element

    object Healthcheck extends Serializable[Healthcheck] {
      override implicit val rw: ReadWriter[Healthcheck] = macroRW
    }

    sealed trait ElementRenderer

    object ElementRenderer {
      implicit val rw: ReadWriter[ElementRenderer] = ReadWriter.merge(
        ElementClassicRenderer.rw,
        ElementFitToParentRenderer.rw
      )

      @upickle.implicits.key("CLASSIC")
      final case class ElementClassicRenderer() extends ElementRenderer

      object ElementClassicRenderer extends Serializable[ElementClassicRenderer] {
        override implicit val rw: ReadWriter[ElementClassicRenderer] = macroRW
      }

      @upickle.implicits.key("FIT_TO_PARENT")
      final case class ElementFitToParentRenderer(minFontSize: Int = 10, maxFontSize: Int = 30) extends ElementRenderer

      object ElementFitToParentRenderer extends Serializable[ElementFitToParentRenderer] {
        override implicit val rw: ReadWriter[ElementFitToParentRenderer] = macroRW
      }
    }
  }
}
