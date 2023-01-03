package org.vollol.ourworkout.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseModel(var id: Long = 0,
                         var title: String="",
                         var name: String=""):Parcelable
