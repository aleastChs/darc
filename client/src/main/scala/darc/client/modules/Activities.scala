/**
  * Created by alexa on 12/07/2017.
  */
package darc.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import darc.client.components._
import darc.client.DarcMain.Location
import diode.data.Pot
import diode.react._

object Activities {
  case class Props(router: RouterCtl[Location], proxy: ModelProxy[Pot[String]])
  case class State(motdWrapper: ReactConnectProxy[Pot[String]])

  private val component = ScalaComponent.builder[Props]("Activities")
    .initialStateFromProps(props => State(props.proxy.connect(m => m)))
    .renderPS { (_, props, state) =>
      <.div(
        <.h2("Activities"),
        state.motdWrapper(Motd(_))
      )
    }
    .build

  def apply(router: RouterCtl[Location], proxy: ModelProxy[Pot[String]]) =
    component(Props(router, proxy))
}