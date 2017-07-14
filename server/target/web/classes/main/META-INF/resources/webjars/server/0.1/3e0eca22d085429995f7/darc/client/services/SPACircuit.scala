/**
  * Created by alexa on 12/07/2017.
  */
package darc.client.services

import autowire._
import diode._
import diode.data._
import diode.util._
import diode.react.ReactConnector
import boopickle.Default._

import darc.shared.{Api, Workout}
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

case object RefreshWorkouts extends Action

case class UpdateAllWorkouts(workouts: Seq[Workout]) extends Action

case class UpdateWorkout(workout: Workout) extends Action

case class DeleteWorkout(workout: Workout) extends Action

case class UpdateMotd(potResult: Pot[String] = Empty) extends PotAction[String, UpdateMotd] {
  override def next(value: Pot[String]) = UpdateMotd(value)
}

case class RootModel(workouts: Pot[Activities], motd: Pot[String])

case class Activities(workouts: Seq[Workout]) {
  def updated(newWorkout: Workout) = {
    workouts.indexWhere(_.id == newWorkout.id) match {
      case -1 =>
        Activities(workouts :+ newWorkout) // add new workout
      case idx =>
        Activities(workouts.updated(idx, newWorkout)) // replace old
    }
  }

  def remove(workout: Workout) = Activities(workouts.filterNot(_ == workout))
}

class ActivityHandler[M](modelRW: ModelRW[M, Pot[Activities]]) extends ActionHandler(modelRW) {
  def handle = {
    case RefreshWorkouts =>
      effectOnly(Effect(AjaxClient[Api].getAllWorkouts().call().map(UpdateAllWorkouts)))
    case UpdateAllWorkouts(workouts) =>
      // new workouts detected, update model
      updated(Ready(Activities(workouts)))
    case UpdateWorkout(workout) =>
      /// make a local update and inform server
      updated(value.map(_.updated(workout)), Effect(AjaxClient[Api].updateWorkout(workout).call().map(UpdateAllWorkouts)))
    case DeleteWorkout(workout) =>
      // make a local update and inform server
      updated(value.map(_.remove(workout)), Effect(AjaxClient[Api].updateWorkout(workout).call().map(UpdateAllWorkouts)))
  }
}

/**
  * Handles actions related to the Motd
  *
  * @param modelRW Reader/Writer to access the model
  */
class MotdHandler[M](modelRW: ModelRW[M, Pot[String]]) extends ActionHandler(modelRW) {
  implicit val runner = new RunAfterJS

  override def handle = {
    case action: UpdateMotd =>
      val updateF = action.effect(AjaxClient[Api].welcomeMsg("Fit-Human").call())(identity _)
      action.handleWith(this, updateF)(PotAction.handler())
  }
}

object SPACircuit extends Circuit[RootModel] with ReactConnector[RootModel] {
  override protected def initialModel = RootModel(Empty, Empty)

  override protected val actionHandler = composeHandlers(
    new ActivityHandler(zoomRW(_.workouts)((m, v) => m.copy(workouts = v))),
    new MotdHandler(zoomRW(_.motd)((m, v) => m.copy(motd = v)))
  )
}