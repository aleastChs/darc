
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/alexa/github/scalajs/darc/server/src/main/resources/routes
// @DATE:Fri Jul 14 11:04:03 CEST 2017

import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset

// @LINE:2
package controllers {

  // @LINE:5
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:5
    def at(file:String): Call = {
      implicit val _rrc = new ReverseRouteContext(Map(("path", "/public/lib/font-awesome/fonts")))
      Call("GET", _prefix + { _defaultPrefix } + "assets/fonts/" + implicitly[PathBindable[String]].unbind("file", file))
    }
  
    // @LINE:6
    def versioned(file:Asset): Call = {
      implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[Asset]].unbind("file", file))
    }
  
  }

  // @LINE:2
  class ReverseApplication(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def logging(): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "logging")
    }
  
    // @LINE:9
    def autowireApi(path:String): Call = {
      import ReverseRouteContext.empty
      Call("POST", _prefix + { _defaultPrefix } + "api/" + implicitly[PathBindable[String]].unbind("path", path))
    }
  
    // @LINE:2
    def index(): Call = {
      import ReverseRouteContext.empty
      Call("GET", _prefix)
    }
  
  }


}
