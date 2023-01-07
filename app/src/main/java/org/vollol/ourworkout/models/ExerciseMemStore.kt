package org.vollol.ourworkout.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long{
    return lastId++
}

class ExerciseMemStore : ExerciseStore {
    val exercises = ArrayList<Exercise>()

    override fun findAll(): List<Exercise>{
        return exercises
    }

    override fun create(exercise: Exercise){
        exercise.id = getId()
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