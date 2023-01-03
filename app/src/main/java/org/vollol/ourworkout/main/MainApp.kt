package org.vollol.ourworkout.main

import android.app.Application
import org.vollol.ourworkout.models.ExerciseMemStore
import org.vollol.ourworkout.models.ExerciseModel

//both imports are needed for logging
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val exercises = ExerciseMemStore()

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        i("ourWorkout started..")
    }
}