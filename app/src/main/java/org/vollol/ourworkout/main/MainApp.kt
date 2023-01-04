package org.vollol.ourworkout.main

import android.app.Application
import org.vollol.ourworkout.models.ExerciseJSONStore
import org.vollol.ourworkout.models.ExerciseStore

//both imports are needed for logging
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var exercises : ExerciseStore

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        exercises = ExerciseJSONStore(applicationContext)

        i("ourWorkout started..")
    }
}