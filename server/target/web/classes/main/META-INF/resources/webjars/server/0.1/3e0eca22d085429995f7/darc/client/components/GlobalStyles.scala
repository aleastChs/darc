/**
  * Created by alexa on 11/07/2017.
  */

package darc.client.components

import darc.client.CssSettings._

object GlobalStyles extends StyleSheet.Inline {
  import dsl._

  style(unsafeRoot("body")(
    paddingTop(70.px)
  ))

  val bootstrapStyles = new BootstrapStyles
}