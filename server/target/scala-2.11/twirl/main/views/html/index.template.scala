
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object index_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

class index extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template3[String,play.api.Configuration,play.api.Environment,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String)(implicit config: play.api.Configuration, env: play.api.Environment):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
import views.html.tags._

Seq[Any](format.raw/*1.85*/("""
"""),format.raw/*3.1*/("""
"""),format.raw/*4.1*/("""<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>"""),_display_(/*8.17*/title),format.raw/*8.22*/("""</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <link rel="stylesheet" media="screen" href="""),_display_(/*10.53*/_asset("stylesheets/main.min.css")),format.raw/*10.87*/(""" """),format.raw/*10.88*/(""">
        <link rel="shortcut icon" type="image/png" href="""),_display_(/*11.58*/_asset("images/favicon.png")),format.raw/*11.86*/(""" """),format.raw/*11.87*/(""">
    </head>
    <body>
        <div id="root">

        </div>
        """),_display_(/*17.10*/scalajs/*17.17*/.html.scripts(projectName = "client", name => _asset(name).toString, name => getClass.getResource(s"/public/$name") != null)),format.raw/*17.141*/("""
    """),format.raw/*18.5*/("""</body>
</html>"""))
      }
    }
  }

  def render(title:String,config:play.api.Configuration,env:play.api.Environment): play.twirl.api.HtmlFormat.Appendable = apply(title)(config,env)

  def f:((String) => (play.api.Configuration,play.api.Environment) => play.twirl.api.HtmlFormat.Appendable) = (title) => (config,env) => apply(title)(config,env)

  def ref: this.type = this

}


}

/**/
object index extends index_Scope0.index
              /*
                  -- GENERATED --
                  DATE: Fri Jul 14 10:38:31 CEST 2017
                  SOURCE: C:/Users/alexa/github/scalajs/darc/server/src/main/twirl/views/index.scala.html
                  HASH: 6f9bf20a8af61d3661fd92f11fa3082520d98c3a
                  MATRIX: 571->1|773->84|801->113|829->115|950->210|975->215|1177->390|1232->424|1261->425|1348->485|1397->513|1426->514|1533->594|1549->601|1695->725|1728->731
                  LINES: 20->1|25->1|26->3|27->4|31->8|31->8|33->10|33->10|33->10|34->11|34->11|34->11|40->17|40->17|40->17|41->18
                  -- GENERATED --
              */
          