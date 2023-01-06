package org.vollol.ourworkout.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(var id: Long = 0,
                    var title: String="",
                    var name: String="",
                    var desc: String="",
                    var unit: Units = Units.NONE,
                    var calories: Int = 0,
                    var weight: Double = 0.0,
                    var repsPerRound: Int = 0,
                    var rounds: Int = 0,
                    var roundDuration: Int = 0,
                    var onTime: Int = 0,
                    var offTime: Int = 0) : Parcelable
{
    enum class Units{
        CAL, KG, NONE
    }
}