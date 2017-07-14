/**
  * Created by alexa on 13/07/2017.
  */

package darc.shared

import boopickle.Default._

sealed trait WorkoutType

case object WorkoutRunning extends WorkoutType

case object WorkoutCycling extends WorkoutType

case object WorkoutStrength extends WorkoutType

case class Workout(id: String, timeStamp: Int, content: String, workoutType: WorkoutType, completed: Boolean)

object WorkoutType {
  implicit val workoutTypePickler: Pickler[WorkoutType] = generatePickler[WorkoutType]
}