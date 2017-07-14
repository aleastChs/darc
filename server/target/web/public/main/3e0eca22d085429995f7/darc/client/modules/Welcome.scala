/**
  * Created by alexa on 13/07/2017.
  */

package darc.client.modules

import darc.client.DarcMain.{Location, ActivitiesLocation}
import darc.client.components._

import diode.data.Pot
import diode.react.{ModelProxy, ReactConnectProxy}

import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Welcome {
  case class Props(router: RouterCtl[Location], proxy: ModelProxy[Pot[String]])
  case class State(motdWrapper: ReactConnectProxy[Pot[String]])

  private val component = ScalaComponent.builder[Props]("Welcome")
    .initialStateFromProps((props) => State(props.proxy.connect(m => m)))
    .renderPS { (_, props, state) =>
      <.div(
        <.h2("Welcome"),
        state.motdWrapper(Motd(_)),
        <.div(props.router.link(ActivitiesLocation)("View your activities"))
      )

    }
    .build

  def apply(routerCtl: RouterCtl[Location], proxy: ModelProxy[Pot[String]]) =
    component(Props(routerCtl, proxy))
}