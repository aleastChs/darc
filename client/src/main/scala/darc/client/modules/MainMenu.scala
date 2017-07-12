/**
  * Created by alexa on 12/07/2017.
  */
package darc.client.modules

import darc.client.components._
import darc.client.components.Bootstrap.CommonStyle
import darc.client.components.Icon._
import darc.client.services._
import darc.client.DarcMain.{ActivitiesLocation, Location, NewActivityLocation, RecordsLocation}
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.extra.router.RouterCtl

import scalacss.ScalaCssReact._

object MainMenu {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(router: RouterCtl[Location], currentLocation: Location, proxy: ModelProxy[Option[Int]])
  private case class MenuItem(idx: Int, label: (Props) => VdomNode, icon: Icon, location: Location)

  private def buildActivitiesMenu(props: Props): VdomElement = {
    val activitiesCount = props.proxy().getOrElse(0)
    <.span(
      <.span("Activities "),
      <.span(
        bss.labelOpt(CommonStyle.info),
        bss.labelAsBadge,
        activitiesCount).when(activitiesCount > 0)
      )
  }

  private val menuItems = Seq(
    MenuItem(1, buildActivitiesMenu, Icon.listAlt, ActivitiesLocation)
    //,
    //MenuItem(2, _ => "New Activities", Icon.plus, NewActivityLocation)
  )

  private class Backend($: BackendScope[Props, Unit]) {
    def mounted(props: Props) = Callback.log(props.toString)
      // Callback

    def render(props: Props) = {
      <.ul(bss.navbar)(
        menuItems.toVdomArray(item =>
          <.li(^.key := item.idx, (^.className := "active").when(props.currentLocation == item.location),
            props.router.link(item.location)(item.icon, " "), item.label(props))
        )
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("MainMenu")
    .renderBackend[Backend]
    .componentDidMount(scope =>
      scope.backend.mounted(scope.props)
    )
    .build

}
