package services

import java.util.{UUID, Date}
import darc.shared._

/**
  * Created by alexa on 12/07/2017.
  */
class ApiService extends Api {
  var workouts = Seq(
    Workout("1", 0x61626364, "Test-lopp", WorkoutRunning, completed = false),
    Workout("2", 0x61626364, "Rullar", WorkoutCycling, completed = false),
    Workout("3", 0x61626364, "Låtsas tränar", WorkoutStrength, completed = true)
  )

  override def welcomeMsg(name: String): String = {
    val date = new Date()
    val msg: String = s"Welcome To DARC, $name! The time is now ${date.getHours}:${date.getMinutes}:" +
      s"${date.getSeconds}, "+ (1900 + (date.getYear)) +". "
    if (date.getHours < 7) msg + "Go back to sleep!"
    else if (date.getHours < 13) msg + "Go out for a run before lunch!"
    else if (date.getHours < 18) msg + "Did you had lunch? Why not a run-break now"
    else msg + "Go out for a Evening-run? Sounds Nice!"
  }

  override def getAllWorkouts(): Seq[Workout] = {
    Thread.sleep(300)
    println(s"Sending ${workouts.size} Workouts")
    workouts
  }

  override def updateWorkout(workout: Workout): Seq[Workout] = {
    if (workouts.exists(_.id == workout.id)) {
      workouts = workouts.collect {
        case i if i.id == workout.id => workout
        case i => i
      }
      println(s"Workout was updated: $workout")
    } else {
      val newWorkout = workout.copy(id = UUID.randomUUID().toString)
      workouts :+ newWorkout
      println(s"Workout was added: $newWorkout")
    }
    Thread.sleep(300)
    workouts
  }

  override def deleteWorkout(itemId: String): Seq[Workout] = {
    println(s"Deleting workout with id = $itemId")
    Thread.sleep(300)
    workouts = workouts.filterNot(_.id == itemId)
    workouts
  }
}
