package com.prodtector.server.services

import cats.data.EitherT
import cats.effect.IO
import com.prodtector.protocol.config.Screen
import upickle.core.AbortException
import upickle.default.read

import scala.concurrent.ExecutionContext
import scala.util.Try

class ConfigService {
  def load(path: String): ServiceResult[Screen] = {

    def readConfig: ServiceResult[String] = {
      EitherT.fromEither {
        Try {
          val source = scala.io.Source.fromFile(path)
          try source.mkString finally source.close()
        }.toEither
      }
    }

    def toScreen(conf: String): ServiceResult[Screen] = {
      EitherT.fromEither {
        Try {
          read[Screen](conf)
        }.toEither
      }
    }

    for {
      conf <- readConfig
      screen <- toScreen(conf)
    } yield screen
  }
}
