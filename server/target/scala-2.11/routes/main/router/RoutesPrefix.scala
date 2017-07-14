
// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/alexa/github/scalajs/darc/server/src/main/resources/routes
// @DATE:Fri Jul 14 11:04:03 CEST 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
