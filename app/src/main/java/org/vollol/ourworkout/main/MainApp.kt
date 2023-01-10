package org.vollol.ourworkout.main

import android.app.Application
import org.vollol.ourworkout.adapters.WorkoutAdapter
import org.vollol.ourworkout.models.ExerciseJSONStore
import org.vollol.ourworkout.models.ExerciseStore
import org.vollol.ourworkout.models.WorkoutJSONStore
import org.vollol.ourworkout.models.WorkoutStore

//both imports are needed for logging
import timber.log.Timber
import timber.log.Timber.i

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

        i("ourWorkout started..")
    }
}