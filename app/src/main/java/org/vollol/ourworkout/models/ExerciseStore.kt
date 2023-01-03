package org.vollol.ourworkout.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long{
    return lastId++
}

interface ExerciseStore {
    fun findAll(): List<ExerciseModel>
    fun create(exercise: ExerciseModel)
    fun update(exercise: ExerciseModel)
}

class ExerciseMemStore : ExerciseStore {
    val exercises = ArrayList<ExerciseModel>()

    override fun findAll(): List<ExerciseModel>{
        return exercises
    }

    override fun create(exercise: ExerciseModel){
        exercise.id = getId()
        exercises.add(exercise)
        logAll()
    }

    override fun update(exercise: ExerciseModel) {
        var foundExercise: ExerciseModel? = exercises.find {p -> p.id == exercise.id}
        if (foundExercise != null) {
            foundExercise.title = exercise.title
            foundExercise.name = exercise.name
            logAll()
        }
    }

    fun logAll() {
        exercises.forEach{ i("${it}") }
    }
}