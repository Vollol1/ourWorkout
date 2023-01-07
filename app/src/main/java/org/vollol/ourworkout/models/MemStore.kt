package org.vollol.ourworkout.models

import timber.log.Timber

var lastExerciseId = 0L
var lastWorkoutBlueprintId = 0L
var lastWorkoutDoneId = 0L

internal fun getExerciseId(): Long{
    return lastExerciseId++
}

internal fun getWorkoutBlueprintId(): Long{
    return lastWorkoutBlueprintId++
}

internal fun getWorkoutDoneId(): Long{
    return lastWorkoutDoneId++
}

class ExerciseMemStore : ExerciseStore {
    val exercises = ArrayList<Exercise>()

    override fun findAll(): List<Exercise>{
        return exercises
    }

    override fun create(exercise: Exercise){
        exercise.id = getExerciseId()
        exercises.add(exercise)
        logAll()
    }

    override fun update(exercise: Exercise) {
        var foundExercise: Exercise? = exercises.find {p -> p.id == exercise.id}
        if (foundExercise != null) {
            foundExercise.title = exercise.title
            foundExercise.name = exercise.name
            foundExercise.desc = exercise.desc
            foundExercise.unit = exercise.unit
            logAll()
        }
    }

    override fun delete(exercise: Exercise) {
        exercises.remove(exercise)
    }

    fun logAll() {
        exercises.forEach{ Timber.i("${it}") }
    }
}