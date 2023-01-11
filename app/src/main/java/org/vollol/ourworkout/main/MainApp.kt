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
        val dateNow = LocalDateTime.now()

        //generate data for debugging
        exercises.create(Exercise(0,"DBL DB Snatch","Snatches","Take a dumbbell and lift it above your head", unit = units[1]))
        exercises.create(Exercise(0,"SB Carry","Sandbag Carry","Carry a Sandbag round by round", unit = units[1]))
        exercises.create((Exercise(0,"Standups", "Standups", "Lay down and stand up", unit = units[2])))
        exercises.create(Exercise(0,"Bike","Biking","Bike till the calories are reached", unit = units[0]))
        var strenEx : MutableList<Exercise> = exercises.findAll().subList(0,2).toMutableList()
        val durEx : MutableList<Exercise> = exercises.findAll().subList(2,4).toMutableList()
        workouts.create(Workout(0, 0, dateNow, "Workout", strenEx, 0, durEx))


        i("ourWorkout started at $dateNow")
    }
}