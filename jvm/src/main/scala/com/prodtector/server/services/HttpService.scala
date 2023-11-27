package com.prodtector.server.services

import cats.*
import cats.data.*
import cats.effect.IO
import com.prodtector.protocol.service.ServiceResponse
import org.http4s.client.Client
import org.http4s.{Request, Uri}

class HttpService(client: Client[IO]) {
  def healthcheck(url: String, expectedResult: Int): ServiceResult[ServiceResponse] = {

    val makeRequest: ServiceResult[Request[IO]] = {
      def toRequest(uri: Uri): Request[IO] = {
        Request()
          .withUri(uri)
      }

      val uri = EitherT.fromEither[IO](Uri.fromString(url))
      uri.bimap(a => Throwable(a.details), toRequest)
    }

    def run(request: Request[IO]): ServiceResult[ServiceResponse] = {
      EitherT.right {
        client.run(request).use { response =>
          val isSuccess = response.status.code == expectedResult

          IO.pure {
            ServiceResponse(
              success = isSuccess,
              response = if (isSuccess) "OK" else "NOK"
            )
          }
        }
      }
    }

    for {
      request <- makeRequest
      response <- run(request)
    } yield response
  }
}
