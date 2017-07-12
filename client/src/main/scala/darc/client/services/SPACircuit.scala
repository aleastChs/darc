/**
  * Created by alexa on 12/07/2017.
  */
package darc.client.services

import autowire._
import diode._
import diode.data._
import diode.util._
import diode.react.ReactConnector
import darc.shared.Api
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case class UpdateMotd(potResult: Pot[String] = Empty) extends PotAction[String, UpdateMotd] {
  override def next(value: Pot[String]) = UpdateMotd(value)
}

case class RootModel(motd: Pot[String])

class MotdHandler[M](modelRW: ModelRW[M, Pot[String]]) extends ActionHandler(modelRW) {
  implicit val runner = new RunAfterJS

  override def handle = {
    case action: UpdateMotd =>
      val updateF = action.effect(AjaxClient[Api].welcomeMsg("Fit-Human").call())(identity _)
      action.handleWith(this, updateF)(PotAction.handler())
  }
}

object SPACircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  override protected def initialModel = RootModel(Empty)

  override protected val actionHandler = composeHandlers(
    new MotdHandler(zoomRW(_.motd)((m, v) => m.copy(motd = v)))
  )
}