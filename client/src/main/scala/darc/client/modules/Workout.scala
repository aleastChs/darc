/**
  * Created by alexa on 13/07/2017.
  */
package darc.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import darc.client.components.Bootstrap._
import darc.client.components._
import darc.client.logger._
import darc.client.services._
import darc.shared._

import diode.react._
import diode.react.ReactPot._
import diode.data.Pot

import scalacss.ScalaCssReact._

object Workout {

  case class Props(proxy: ModelProxy[Pot[Activities]])

  case class State(selectedWorkout: Option[Workout] = None, showWorkoutForm: Boolean = false)

  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props) =
      Callback.when(props.proxy().isEmpty)(props.proxy.dispatchCB(RefreshWorkouts))

    def editWorkout(workout: Option[Workout]) =
      $.modState(s => s.copy(selectedWorkout = workout, showWorkoutForm = true))

    def workoutEdited(workout: Workout, completed: Boolean) = {
      val callback = if (completed) {
        Callback.log("Workout editing completed")
      } else {
        Callback.log(s"Workout edited: $workout") >>
          $.props >>= (_.proxy.dispatchCB(UpdateWorkout(workout)))
      }

      callback >> $.modState(s => s.copy(showWorkoutForm = false))
    }

    def render(props: Props, state: State) =
      Panel(
        Panel.Props("Your Workouts"),
        <.div(
          props.proxy().renderFailed(ex => "Error Loading Workouts"),
          props.proxy().renderPending(_ > 100, _ => "Loading..."),
          props.proxy().render(activities => WorkoutList(activities.workouts, workout => props.proxy.dispatchCB(UpdateWorkout(workout)),
            workout => editWorkout(Some(workout)), workout => props.proxy.dispatchCB(DeleteWorkout(workout)))),
          Button(
            Button.Props(editWorkout(None)), Icon.plusCircle, " New"
          )
        ),
        if (state.showWorkoutForm) WorkoutForm(WorkoutForm.Props(state.selectedWorkout, workoutEdited))
        else VdomArray.empty()
      )
  }

  val component = ScalaComponent.builder[Props]("Workout")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  def apply(proxy: ModelProxy[Pot[Activities]]) = component(Props(proxy))

}

object WorkoutForm {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(workout: Option[Workout], submitHandler: (Workout, Boolean) => Callback)
  case class State(workout: Workout, completed: Boolean = true)

  class Backend($: BackendScope[Props, State]) {
    def submitForm(): Callback = {
      $.modState(s => s.copy(completed = false))
    }

    def formClosed(state: State, props: Props): Callback =
      props.submitHandler(state.workout, state.completed)

    def updateDescription(e: ReactEventFromInput) = {
      val text = e.target.value
      $.modState(s => s.copy(workout = s.workout.copy(content = text)))
    }

    def updateType(e: ReactEventFromInput) = {
      val newPri = e.currentTarget.value match {
        case p if p == WorkoutRunning.toString => WorkoutRunning
        case p if p == WorkoutCycling.toString => WorkoutCycling
        case p if p == WorkoutStrength.toString => WorkoutStrength
      }
      $.modState(s => s.copy(workout = s.workout.copy(workoutType = newPri)))
    }

    def render(props: Props, state: State) = {
      log.debug(s"User is ${if (state.workout.id == "") "adding" else "editing"} a workout")
      val headerText = if (state.workout.id == "") "Add new Workout" else "Edit Workout"
      Modal(
        Modal.Props(
          header = hide =>
            <.span(
              <.button(
                  ^.tpe := "button", bss.close, ^.onClick --> hide, Icon.close), <.h4(headerText)
              ),
          footer = hide =>
            <.span(
              Button(
                Button.Props(
                  submitForm() >> hide
                )
              )
            ),
          closed = formClosed(state,props)
        ),
        <.div(
          bss.formGroup,
          <.label(
              ^.`for` := "description", "Description"
          ),
          <.input.text(
            bss.formControl,
            ^.id := "description",
            ^.value := state.workout.content,
            ^.placeholder := "write description",
            ^.onChange ==> updateDescription
          )
        ),
        <.div(
          bss.formGroup,
          <.label(
              ^.`for` := "type", "Workout Type"
          ),
          <.select(
            bss.formControl,
            ^.id := "completed",
            ^.value := state.workout.completed.toString,
            ^.onChange ==> updateType,
            <.option(
                ^.value := WorkoutRunning.toString,
              "Running"
            ),
            <.option(
                ^.value := WorkoutCycling.toString,
              "Cycling"
            ),
            <.option(
                ^.value := WorkoutStrength.toString,
              "Strength"
            )
          )
        )
      )
    }
  }

  val component = ScalaComponent.builder[Props]("WorkoutForm")
    .initialStateFromProps(props => State(props.workout.getOrElse(darc.shared.Workout("", 0, "", WorkoutRunning, completed = false))))
    .renderBackend[Backend]
    .build

  def apply(props: Props) = component(props)
}
