/**
  * Created by alexa on 12/07/2017.
  */
package controllers

import java.nio.ByteBuffer

import boopickle.Default._
import com.google.inject.Inject
import play.api.{Configuration, Environment}
import play.api.mvc._
import services._
import darc.shared.Api

import scala.concurrent.ExecutionContext.Implicits.global

object Router extends autowire.Server[ByteBuffer, Pickler, Pickler] {
  def read[Result: Pickler](p: ByteBuffer) = Unpickle[Result].fromBytes(p)
  def write[Result: Pickler](r: Result) = Pickle.intoBytes(r)
}

class Application @Inject() (implicit val config: Configuration, env: Environment) extends Controller {
  val apiService = new ApiService()

  def index = Action {
    Ok(views.html.index("DARC"))
  }

  def autowireApi(path: String) = Action.async(parse.raw) {
    implicit request =>
      println(s"Request path: $path")

      val b = request.body.asBytes(parse.UNLIMITED).get

      Router.route[Api](apiService)(
        autowire.Core.Request(path.split("/"), Unpickle[Map[String, ByteBuffer]].fromBytes(b.asByteBuffer))
      ).map(buffer => {
        val data = Array.ofDim[Byte](buffer.remaining())
        buffer.get(data)
        Ok(data)
      })
  }

  def logging = Action(parse.anyContent) {
    implicit request =>
      request.body.asJson.foreach {
        msg =>
          println(s"CLIENT - $msg")
      }
      Ok("")
  }
}
