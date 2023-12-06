package com.prodtector.front.component

import com.raquo.laminar.api.L.*
import com.raquo.laminar.modifiers.Modifier
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import org.scalajs.dom.HTMLDivElement

import scala.scalajs.js

object FitTextToParentComponent {
  def apply(text: String, minFontSize: Int, maxFontSize: Int): Element = {
    apply(Var(text).signal, minFontSize, maxFontSize)
  }

  def apply(text: Signal[String], minFontSize: Int, maxFontSize: Int): Element = {
    val t = span(child.text <-- text)
    val d = div(
      cls := "fit-to-parent",
      t
    )

    def shrink(): Unit = {
      val ref = t.ref
      val parent = t.ref.parentElement.parentElement

      val ratio = ref.clientWidth.toDouble / parent.clientWidth.toDouble
      println(ratio)

      val fontSize: Double = {
        dom.window
          .getComputedStyle(ref)
          .getPropertyValue("font-size")
          .replace("px", "")
          .toDoubleOption
          .getOrElse(20.0)
      }

      val newSize = {
        val tmp = fontSize / ratio

        if (tmp < minFontSize) minFontSize
        else if (tmp > maxFontSize) maxFontSize
        else tmp
      }

      ref.style.fontSize = s"""${newSize}px"""
    }

    val dataObserver = Observer[String](onNext = x => shrink())

    def observe(owner: Owner): Unit = {
      text.addObserver(dataObserver)(owner)
    }

    d.amend(
      onMountCallback(ctx => observe(ctx.owner))
    )
  }
}
