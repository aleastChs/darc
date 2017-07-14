/**
  * Created by alexa on 12/07/2017.
  */
package darc.client.services

import boopickle.Default._
import java.nio.ByteBuffer

import boopickle.Default
import org.scalajs.dom

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.typedarray._

object AjaxClient extends autowire.Client[ByteBuffer, Pickler, Pickler] {
  override def doCall(request: Request): Future[ByteBuffer] = {
    dom.ext.Ajax.post(
      url = "/api/" + request.path.mkString("/"),
      data = Pickle.intoBytes(request.args),
      responseType = "arraybuffer",
      headers = Map("Content-Type" -> "application/octet-stream")
    ).map(r => TypedArrayBuffer.wrap(r.response.asInstanceOf[ArrayBuffer]))
  }

  override def read[Result: Pickler](p: ByteBuffer) = Unpickle[Result].fromBytes(p)

  override def write[Result: Pickler](r: Result) = Pickle.intoBytes(r)
}
