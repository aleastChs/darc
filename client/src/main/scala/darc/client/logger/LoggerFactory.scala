/**
  * Created by alexa on 12/07/2017.
  */

package darc.client.logger

import scala.annotation.elidable
import scala.annotation.elidable._

trait Logger {
  @elidable(FINEST) def trace(msg: String, e: Exception): Unit
  @elidable(FINEST) def trace(msg: String): Unit
  @elidable(FINE) def debug(msg: String, e: Exception): Unit
  @elidable(FINE) def debug(msg: String): Unit
  @elidable(INFO) def info(msg: String, e: Exception): Unit
  @elidable(INFO) def info(msg: String): Unit
  @elidable(WARNING) def warn(msg: String, e: Exception): Unit
  @elidable(WARNING) def warn(msg: String): Unit
  @elidable(SEVERE) def error(msg: String, e: Exception): Unit
  @elidable(SEVERE) def error(msg: String): Unit
  @elidable(SEVERE) def fatal(msg: String, e: Exception): Unit
  @elidable(SEVERE) def fatal(msg: String): Unit

  def enableServerLogging(url: String): Unit
  def disableServerLogging(): Unit
}

object LoggerFactory {
  private[logger] def createLogger(name: String) = {}

  lazy val consoleAppender = new BrowserConsoleAppender
  lazy val popUpAppender = new PopUpAppender

  def getLogger(name: String): Logger = {
    val nativeLogger = Log4JavaScript.log4javascript.getLogger(name)
    nativeLogger.addAppender(consoleAppender)
    new L4JSLogger(nativeLogger)
  }

  def getPopUpLogger(name: String): Logger = {
    val nativeLogger = Log4JavaScript.log4javascript.getLogger(name)
    nativeLogger.addAppender(popUpAppender)
    new L4JSLogger(nativeLogger)
  }
}