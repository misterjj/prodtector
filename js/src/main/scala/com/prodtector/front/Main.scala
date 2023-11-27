package com.prodtector.front

import com.prodtector.front.component.tile.TileComponent
import com.prodtector.protocol.config.Screen
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import upickle.*
import upickle.default.{Reader, read}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js.Thenable.Implicits.thenable2future
import scala.util.{Failure, Success}

@main
def Prodtector(): Unit = {
  Main.readConfig().onComplete {
    case Failure(exception) =>
      render(dom.document.querySelector("#app"), Main.error(exception))

    case Success(el) =>
      render(dom.document.querySelector("#app"), el)
  }
}

object Main {
  def fetch[T](path: String)(implicit reader: Reader[T]): Future[T] = {
    val backendUrl = "http://127.0.0.1:8080"
    for {
      response <- dom.fetch(backendUrl + path)
      text <- response.text()
    } yield read[T](text)
  }


  def readConfig()(implicit ec: ExecutionContext): Future[Element] = {
    fetch[Screen]("/config").map(appElement)
  }

  def appElement(screen: Screen): Element = {
    div(
      cls := s"app-container",
      cls := s"columns-${screen.columns}",
      cls := s"rows-${screen.rows}",
      screen.tiles.map(TileComponent(_).build())
    )
  }

  def error(exception: Throwable): Element = {
    println(exception)
    div("error")
  }
}