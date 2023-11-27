package com.prodtector.server

import cats.effect.*
import cats.implicits.toSemigroupKOps
import com.comcast.ip4s.*
import com.prodtector.protocol.config.model.Screen
import com.prodtector.protocol.service.ServiceResponse
import com.prodtector.server.config.MainConfig
import com.prodtector.server.services.{ConfigService, HttpService, ServiceResult}
import com.typesafe.config.{Config, ConfigFactory}
import org.http4s.client.{Client, JavaNetClientBuilder}
import org.http4s.dsl.io.*
import org.http4s.ember.server.*
import org.http4s.headers.`Content-Type`
import org.http4s.implicits.*
import org.http4s.server.middleware.CORS
import org.http4s.{HttpRoutes, MediaType, Method, Request, Response}
import upickle.default.{ReadWriter, write}

object Main extends IOApp {
  val config: Config = ConfigFactory.load()
  val mainConfig: MainConfig = MainConfig.from(config.getConfig("app"))

  private def makeResponse[T](handler: ServiceResult[T])(implicit wr: ReadWriter[T]): IO[Response[IO]#Self] = {
    val response: IO[Response[IO]] = handler.value.flatMap {
      case Left(value) => Forbidden(s"$value")
      case Right(value) =>
        val json: String = write(value)
        Ok(json)
    }

    response.map(_.withContentType(`Content-Type`(new MediaType("application", "json"))))
  }

  val configService: ConfigService = ConfigService()

  val client: Client[IO] = JavaNetClientBuilder[IO].create
  val httpService: HttpService = new HttpService(client)

  private val configRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "config" =>
      val call: ServiceResult[Screen] = configService.load(mainConfig.configPath)
      makeResponse(call)
  }

  private val httpServiceRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "service" / "http" / "healthcheck" =>
      val call: ServiceResult[ServiceResponse] = httpService.healthcheck("https://www.google.com", 200)
      makeResponse(call)
  }

  // todo: Deprecated ???
  private val corsConfig = {
    CORS
      .DefaultCORSConfig
      .withAllowCredentials(false)
      .withAnyMethod(false)
      .withAllowedMethods(Some(Set(Method.GET)))
  }

  private val routes = CORS(
    configRoutes <+> httpServiceRoutes,
    corsConfig
  )

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
