/**
  * Created by alexa on 11/07/2017.
  */

package darc.client.components

import org.scalajs
import scalajs.dom._
import scala.scalajs.js
import js.annotation.JSGlobal

// Facade for JQuery

@js.native
trait JQueryEventObject extends Event {
  var data: js.Any = js.native
}

@js.native
@JSGlobal("jQuery")
object JQueryStatic extends js.Object {
  def apply(element: Element): JQuery = js.native
}

@js.native
trait JQuery extends js.Object {
  def on(events: String, selector: js.Any, data: js.Any, handler: js.Function1[JQueryEventObject, js.Any]): JQuery = js.native
  def off(events: String): JQuery = js.native
}