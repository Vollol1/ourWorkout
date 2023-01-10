package org.vollol.ourworkout.main

import android.app.Application
import org.vollol.ourworkout.R
import org.vollol.ourworkout.adapters.WorkoutAdapter
import org.vollol.ourworkout.models.*

//both imports are needed for logging
import timber.log.Timber
import timber.log.Timber.i
import java.time.LocalDateTime

class MainApp : Application() {

    lateinit var exercises : ExerciseStore
    lateinit var workouts : WorkoutStore
    lateinit var doneWorkouts : WorkoutStore

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        exercises = ExerciseJSONStore(applicationContext)
        workouts = WorkoutJSONStore(applicationContext, false)
        doneWorkouts = WorkoutJSONStore(applicationContext, true)

        val units = resources.getStringArray(R.array.exercise_activity_units)

        //generate data for debugging
        exercises.create(Exercise(0,"DBD DB","DBD DB","Description", units[0]))
        exercises.create(Exercise(0,"Bike","Bikeing","Description", units[1]))
        workouts.create(Workout(0, 0, LocalDateTime.now(), "Workout", exercises.findAll() as MutableList<Exercise>, 0, exercises.findAll() as MutableList<Exercise>))


        i("ourWorkout started..")
    }
}