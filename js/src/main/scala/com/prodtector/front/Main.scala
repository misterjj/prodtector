package com.prodtector.front

import com.prodtector.protocol.config.model.Screen
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import upickle.*
import upickle.default.read

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js.Thenable.Implicits.thenable2future
import scala.util.{Failure, Success}

@main
def Prodtector(): Unit = {
  Main.readConfig().onComplete {
    case Failure(exception) =>
      render(dom.document.querySelector("#app"), Main.error())

    case Success(el) =>
      render(dom.document.querySelector("#app"), el)
  }
}

object Main {
  def readConfig()(implicit ec: ExecutionContext): Future[Element] = {
    val q = new dom.URL(dom.window.location.href).searchParams
    val configPath = Option(q.get("config"))

    configPath match
      case Some(config) =>
        for {
          response <- dom.fetch(config)
          text <- response.text()
        } yield {
          val screen = read[Screen](text)
          appElement(screen)
        }

      case None =>
        Future.successful(error())
  }

  def appElement(screen: Screen): Element = {
    div(
      h1("Hello Scala JS and vite !"),
      div(s"config : ${screen}"),
      button("test")
    )
  }

  def error(): Element = {
    div("error")
  }
}