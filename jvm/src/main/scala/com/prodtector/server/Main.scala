package com.prodtector.server

import cats.implicits.*
import cats.data.{EitherT, Kleisli, Writer}
import cats.effect.*
import com.comcast.ip4s.*
import com.prodtector.protocol.config.model.Screen
import com.prodtector.server.config.MainConfig
import com.prodtector.server.services.ConfigService
import com.typesafe.config.{Config, ConfigFactory}
import org.http4s.dsl.io.*
import org.http4s.ember.server.*
import org.http4s.headers.`Content-Type`
import org.http4s.implicits.*
import org.http4s.{HttpRoutes, MediaType, Request, Response}
import upickle.default.{ReadWriter, write}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Main extends IOApp {
  val config: Config = ConfigFactory.load()
  val mainConfig: MainConfig = MainConfig.from(config.getConfig("app"))
  println(mainConfig)

  private def makeResponse[T](handler: EitherT[Future, Throwable, T])(implicit wr: ReadWriter[T]): IO[Response[IO]#Self] = {
    val response = IO.fromFuture(IO(handler.value)).flatMap {
      case Left(value) => Forbidden(s"$value")
      case Right(value) =>
        val json: String = write(value)
        Ok(json)
    }

    response
      .map(_.withContentType(`Content-Type`(new MediaType("application", "json"))))
  }

  val configService: ConfigService = ConfigService()

  private val configRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "config" =>
      val call: EitherT[Future, Throwable, Screen] = configService.load(mainConfig.configPath)

      makeResponse(call)
  }

  private val routes = configRoutes /*<+> config2Routes*/

  def run(args: List[String]): IO[ExitCode] = {
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(routes.orNotFound)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}
