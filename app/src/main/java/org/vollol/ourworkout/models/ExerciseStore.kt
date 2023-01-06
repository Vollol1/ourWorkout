package org.vollol.ourworkout.models

interface ExerciseStore {
    fun findAll(): List<Exercise>
    fun create(exercise: Exercise)
    fun update(exercise: Exercise)
    fun delete(exercise: Exercise)
}

