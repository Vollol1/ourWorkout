package org.vollol.ourworkout.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime


@Parcelize
data class Exercise(var id: Long = 0,
                    var title: String="",
                    var name: String="",
                    var desc: String="",
                    var isEndurance: Boolean = false,
                    var unit: String="",
                    var calories: Int = 0,
                    var weight: Double = 0.0,
                    var repsPerRound: Int = 0,
                    var rounds: Int = 0,
                    var round: Int = 0,
                    var roundDuration: Int = 0,
                    var onTime: Int = 0,
                    var offTime: Int = 0) : Parcelable

@Parcelize
data class Workout(var blueprintId: Long = 0,
                   var title: String = "",
                   var strengthExercises: MutableList<Exercise> = mutableListOf<Exercise>(),
                   var strengthDuration: Int = 0,
                   var enduranceExercises: MutableList<Exercise> = mutableListOf<Exercise>(),
                   var enduranceRounds: Int = 0,
                   var enduranceDuration: Int = 0) : Parcelable


@Parcelize
data class DoAbleWorkout(var workoutDoneId: Long = 0,
                   var timeStamp: LocalDateTime = LocalDateTime.now(),
                   var title: String = "",
                   var exercises: MutableList<Exercise> = mutableListOf<Exercise>(),
                   var strengthDuration: Int = 0,
                   var enduranceRounds: Int = 0,
                   var enduranceDuration: Int = 0) : Parcelable
