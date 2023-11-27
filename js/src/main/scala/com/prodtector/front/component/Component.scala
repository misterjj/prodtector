package com.prodtector.front.component

import com.raquo.laminar.api.L.*
import com.raquo.laminar.modifiers.Modifier
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom.HTMLDivElement

trait Component {
  def build(modifiers: Modifier[ReactiveHtmlElement[HTMLDivElement]]*): Element
}
