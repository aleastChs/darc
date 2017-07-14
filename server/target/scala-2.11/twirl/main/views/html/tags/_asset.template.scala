
package views.html.tags

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object _asset_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

class _asset extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template2[String,play.api.Configuration,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(path: String)(implicit configuration: play.api.Configuration):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](_display_(/*1.65*/{configuration.getString("application.cdn").getOrElse("") + routes.Assets.versioned(path)}))
      }
    }
  }

  def render(path:String,configuration:play.api.Configuration): play.twirl.api.HtmlFormat.Appendable = apply(path)(configuration)

  def f:((String) => (play.api.Configuration) => play.twirl.api.HtmlFormat.Appendable) = (path) => (configuration) => apply(path)(configuration)

  def ref: this.type = this

}


}

/**/
object _asset extends _asset_Scope0._asset
              /*
                  -- GENERATED --
                  DATE: Thu Jul 13 08:15:36 CEST 2017
                  SOURCE: C:/Users/alexa/github/scalajs/darc/server/src/main/twirl/views/tags/_asset.scala.html
                  HASH: ee19018f9b06b14499e08008cf758ac14abdfde4
                  MATRIX: 557->1|714->64
                  LINES: 20->1|25->1
                  -- GENERATED --
              */
          