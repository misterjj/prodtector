package com.prodtector.front

import org.scalajs.dom
import org.scalajs.dom.{HttpMethod, Request, RequestInit, RequestMode}
import upickle.default.{Reader, Writer, read, write}

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.js
import scala.scalajs.js.Thenable.Implicits.thenable2future

object Tool {
  def fetch[R](path: String)(implicit reader: Reader[R], ec: ExecutionContext): Future[R] = {
    for {
      response <- dom.fetch("http://127.0.0.1:8080" + path)
      text <- response.text()
    } yield read[R](text)
  }

  def post[T, R](
                  path: String,
                  body: T,
                  mode: RequestMode = RequestMode.cors
                )(
                  implicit reader: Reader[R],
                  writer: Writer[T],
                  ec: ExecutionContext
                ):
  Future[R] = {
    val init = js.Dynamic.literal(method = HttpMethod.POST, mode = mode, body = write(body)).asInstanceOf[RequestInit]
    val request = Request("http://127.0.0.1:8080" + path, init)
    for {
      response <- dom.fetch(request)
      text <- response.text()
    } yield {
      println(text)
      read[R](text)
    }
  }
}
