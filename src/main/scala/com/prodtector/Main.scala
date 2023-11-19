package com.prodtector

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}

@main
def Prodtector(): Unit = {
  val q = new dom.URL(dom.window.location.href).searchParams
  println(q.get("config")) // https://stackoverflow.com/questions/19441400/working-with-yaml-for-scala

  renderOnDomContentLoaded(dom.document.querySelector("#app"), Main.appElement())
}

object Main {
  def appElement(): Element = {
    div(
      h1("Hello Scala JS and vite"),
      button("test")
    )
  }
}