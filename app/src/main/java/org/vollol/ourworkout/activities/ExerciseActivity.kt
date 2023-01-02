package org.vollol.ourworkout.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.vollol.ourworkout.R
import org.vollol.ourworkout.databinding.ActivityExerciseBinding
import org.vollol.ourworkout.main.MainApp
import org.vollol.ourworkout.models.ExerciseModel
import timber.log.Timber.i


class ExerciseActivity : AppCompatActivity() {

    /* ActivityExerciseBinding - this is an auto generated class, which allows to have access on all
    *  content defined in the according layout description (.xml-file)
    *  In this case -> activity_exercise.xml
    */
    private lateinit var binding: ActivityExerciseBinding

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflate Layout to the screen, which is defined in the .xml file
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //including the menu-toolbar
        binding.toolbarCancel.title = title
        setSupportActionBar(binding.toolbarCancel)

        //initialize app from instantiated MainApp-Class
        app = application as MainApp
        i("Exercise Activity started..")

        binding.btnAdd.setOnClickListener() {
            val exercise = ExerciseModel(binding.exerciseTitle.text.toString(),
                                        binding.exerciseName.text.toString())

            if(exercise.title.isNotEmpty() and exercise.name.isNotEmpty()) {
                app.exercises.add(exercise.copy())
                i("add Button Pressed: $exercise.title, $exercise.name")

                //finish activity- to end intend, which is created over an other activity
                setResult(RESULT_OK)
                finish()
            }
            else{
                Snackbar
                    .make(it,"Please Enter a title and name", Snackbar.LENGTH_LONG)
                    .show()
            }

            i("all added exercises till now:")
            for(ex in app.exercises){
                val title = ex.title.toString()
                val name = ex.name.toString()
                i("Title: $title| Name: $name")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_exercise_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}