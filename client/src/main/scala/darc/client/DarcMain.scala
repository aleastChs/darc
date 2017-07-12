/**
  * Created by alexa on 11/07/2017.
  */

package darc.client

import darc.client.components.GlobalStyles
import darc.client.logger._
import darc.client.modules._
import darc.client.services.SPACircuit

import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.extra.router._

import org.scalajs.dom
import CssSettings._
import scalacss.ScalaCssReact._
import scala.scalajs.js
import js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("DarcMain")
object DarcMain extends js.JSApp {
  sealed trait Location
  case object ActivitiesLocation extends Location
  case object RecordsLocation extends Location
  case object NewActivityLocation extends Location

  val routerConfig = RouterConfigDsl[Location].buildConfig { dsl =>
    import dsl._

    (staticRoute(root, ActivitiesLocation) ~> renderR(ctl =>
      SPACircuit.wrap(_.motd)(proxy => Activities(ctl, proxy)))
//      | staticRoute("#newActivity", NewActivityLocation) ~> renderR(ctl =>
//      Nothing)
      ).notFound(redirectToPage(ActivitiesLocation)(Redirect.Replace))
  }.renderWith(layout)

  def layout(c: RouterCtl[Location], r: Resolution[Location]) = {
    <.div(
      // here we use plain Bootstrap class names as these are specific to the top level layout defined here
      <.nav(^.className := "navbar navbar-inverse navbar-fixed-top",
        <.div(^.className := "container",
          <.div(^.className := "navbar-header", <.span(^.className := "navbar-brand", "DARC")),
          <.div(^.className := "collapse navbar-collapse",

          )
        )
      ),
      // currently active module is shown in this container
      <.div(^.className := "container", r.render())
    )
  }
  @JSExport
  def main(): Unit = {
    log.warn("Application starting")

    log.enableServerLogging("/logging")
    log.info("Server is now enabled for messages")

    GlobalStyles.addToDocument()

    val router = Router(BaseUrl.until_#, routerConfig)
    router().renderIntoDOM(dom.document.getElementById("root"))
  }
}