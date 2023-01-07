package org.vollol.ourworkout.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Exercise(var id: Long = 0,
                    var title: String="",
                    var name: String="",
                    var desc: String="",
                    var unit: String="",
                    var calories: Int = 0,
                    var weight: Double = 0.0,
                    var repsPerRound: Int = 0,
                    var rounds: Int = 0,
                    var roundDuration: Int = 0,
                    var onTime: Int = 0,
                    var offTime: Int = 0) : Parcelable

@Parcelize
data class Workout(var blueprintId: Long = 0,
                   var workoutDoneId: Long = 0,
                   var timeStamp: Date,
                   var title: String,
                   var strengthExercise: List<Exercise>,
                   var strengthDuration: Int,
                   var enduranceExercise: List<Exercise>,
                   var enduranceRounds: Int,
                   var enduranceDuration: Int) : Parcelable