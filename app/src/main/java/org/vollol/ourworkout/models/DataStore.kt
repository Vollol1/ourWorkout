package org.vollol.ourworkout.models

interface ExerciseStore {
    fun findAll(): List<Exercise>
    fun create(exercise: Exercise)
    fun update(exercise: Exercise)
    fun delete(exercise: Exercise)
}

interface WorkoutStore {
    fun findAll(): List<Workout>
    fun create(workout: Workout)
    fun update(workout: Workout)
    fun delete(workout: Workout)
}

interface DoAbleWorkoutStore {
    fun findAll(): List<DoAbleWorkout>
    fun create(workout: DoAbleWorkout)
    fun update(workout: DoAbleWorkout)
    fun delete(workout: DoAbleWorkout)
}

