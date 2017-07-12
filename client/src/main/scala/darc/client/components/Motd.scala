/**
  * Created by alexa on 12/07/2017.
  */
package darc.client.components

import diode.react.ReactPot._
import diode.react._
import diode.data.Pot

import japgolly.scalajs.react
import react._
import react.vdom.html_<^._
import darc.client.components.Bootstrap._


object Motd {
  val Motd = ScalaComponent.builder[ModelProxy[Pot[String]]]("Motd")
    .render_P { proxy =>
      Panel(Panel.Props("Message of the day"),
        // render messages depending on the state of the Pot
        proxy().renderPending(_ > 500, _ => <.p("Loading...")),
        proxy().renderFailed(ex => <.p("Failed to load")),
        proxy().render(m => <.p(m)),
        Button(Button.Props(proxy.dispatchCB(UpdateMotd()), CommonStyle.danger), Icon.refresh, " Update")
      )
    }
    .componentDidMount( scope =>
      Callback.when(scope.props.value.isEmpty)(scope.props.dispatchCB(UpdateMotd()))
    )
    .build

  def apply(proxy: ModelProxy[Pot[String]]) = Motd(proxy)
}
