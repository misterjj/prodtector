package com.prodtector.server

import cats.data.EitherT

import scala.concurrent.Future

package object services {
  type ServiceResult[T] = EitherT[Future, Throwable, T]
}
