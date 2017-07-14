/**
  * Created by alexa on 13/07/2017.
  */
package darc.client.components

import darc.client.components.Bootstrap.{CommonStyle, Button}
import darc.shared._

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scalacss.ScalaCssReact._

object WorkoutList {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class WorkoutListProps(
                             workouts: Seq[Workout],
                             stateChanged: Workout => Callback,
                             editWorkout: Workout => Callback,
                             deleteWorkout: Workout => Callback
                             )

  private val WorkoutListComponent = ScalaComponent.builder[WorkoutListProps]("Workout")
    .render_P(p => {
      val style = bss.listGroup
      def renderWorkout(workout: Workout) = {
        val workoutStyle = workout.workoutType match {
          case WorkoutRunning => style.itemOpt(CommonStyle.success)
          case WorkoutCycling => style.itemOpt(CommonStyle.warning)
          case WorkoutStrength => style.itemOpt(CommonStyle.info)
        }
        <.li(workoutStyle,
          <.input.checkbox(^.checked := workout.completed, ^.onChange --> p.stateChanged(workout.copy(completed = !workout.completed))),
          <.span(" "),
          if (workout.completed) <.s(workout.content) else <.span(workout.content),
          Button(Button.Props(p.editWorkout(workout), addStyles = Seq(bss.pullRight, bss.buttonXS)), "Edit"),
          Button(Button.Props(p.deleteWorkout(workout), addStyles = Seq(bss.pullRight, bss.buttonXS)), "Delete")
        )
      }
      <.ul(style.listGroup)(p.workouts toTagMod renderWorkout)
    })
    .build

  def apply(workouts: Seq[Workout], stateChange: Workout => Callback, editItem: Workout => Callback, deleteItem: Workout => Callback) =
    WorkoutListComponent(WorkoutListProps(workouts, stateChange, editItem, deleteItem))
}
