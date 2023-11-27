package com.prodtector.server

import cats.data.EitherT
import cats.effect.IO

import scala.concurrent.Future

package object services {
  type ServiceResult[T] = EitherT[IO, Throwable, T]
}
