/**
  * Created by alexa on 11/07/2017.
  */
package darc.shared

trait Api {
  def welcomeMsg(name: String): String

  def getAllWorkouts(): Seq[Workout]

  def updateWorkout(workout: Workout): Seq[Workout]

  def deleteWorkout(itemId: String): Seq[Workout]
}
