package org.vollol.ourworkout.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.vollol.ourworkout.databinding.ActivityExerciseBinding
import org.vollol.ourworkout.models.ExerciseModel
//both imports are needed for logging
import timber.log.Timber
import timber.log.Timber.i

class ExerciseActivity : AppCompatActivity() {

    /* ActivityExerciseBinding - this is an auto generated class, which allows to have access on all
    *  content defined in the according layout description (.xml-file)
    *  In this case -> activity_exercise.xml
    */
    private lateinit var binding: ActivityExerciseBinding

    val exercises = ArrayList<ExerciseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflate Layout to the screen, which is defined in the .xml file
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Exercise Activity started..")

        binding.btnAdd.setOnClickListener() {
            val exercise = ExerciseModel(binding.exerciseTitle.text.toString(),
                                        binding.exerciseName.text.toString())

            if(exercise.title.isNotEmpty() and exercise.name.isNotEmpty()) {
                exercises.add(exercise.copy())
                i("add Button Pressed: $exercise.title, $exercise.name")
            }
            else{
                Snackbar
                    .make(it,"Please Enter a title and name", Snackbar.LENGTH_LONG)
                    .show()
            }

            i("all added exercises till now:")
            for(ex in exercises){
                val title = ex.title.toString()
                val name = ex.name.toString()
                i("Title: $title| Name: $name")
            }
        }
    }
}