package darc.client

/**
  * Created by alexa on 12/07/2017.
  */
package object logger {
  private val defaultLogger = LoggerFactory.getLogger("Log")

  def log = defaultLogger
}
