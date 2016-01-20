package com.topteam.core

import spray.routing._
import spray.routing.directives.RespondWithDirectives
import spray.http.MediaTypes._
import spray.httpx.marshalling.Marshaller
import spray.http.ContentTypes
import org.json4s.native.Serialization
import org.json4s.{DefaultFormats, Formats}
import spray.httpx.encoding.Gzip
import akka.actor.ActorSystem

/**
 * Created by JiangFeng on 2014/4/29.
 */
abstract class DefaultDirectives(implicit system:ActorSystem) extends Directives {

  implicit val route: Route

  implicit def json4sFormats: Formats = DefaultFormats


  def responseJson(obj: AnyRef): Route = {
    respondWithMediaType(`application/json`) {
      implicit def json4sMarshaller[T <: AnyRef] =
        Marshaller.delegate[T, String](ContentTypes.`application/json`)(Serialization.write(_))
      complete {
        obj
      }
    }
  }

  def responseHtml(html: String): Route = {
    respondWithMediaType(`text/html`) {
      encodeResponse(Gzip) {
        complete(html)
      }
    }
  }

}
