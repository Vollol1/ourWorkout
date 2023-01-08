package org.vollol.ourworkout.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import org.vollol.ourworkout.R
import org.vollol.ourworkout.databinding.ActivityExerciseBinding
import org.vollol.ourworkout.databinding.ActivityManageWorkoutBinding
import org.vollol.ourworkout.main.MainApp
import org.vollol.ourworkout.models.Exercise
import org.vollol.ourworkout.models.Workout
import timber.log.Timber.i

class ManageWorkoutActivity : AppCompatActivity(){

    /* ActivityExerciseBinding - this is an auto generated class, which allows to have access on all
    *  content defined in the according layout description (.xml-file)
    *  In this case -> activity_exercise.xml
    */
    private lateinit var binding: ActivityManageWorkoutBinding

    lateinit var app: MainApp

    var workout = Workout()
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflate Layout to the screen, which is defined in the .xml file
        binding = ActivityManageWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //including the menu-toolbar
        binding.toolbarCancel.title = title
        setSupportActionBar(binding.toolbarCancel)

        //initialize app from instantiated MainApp-Class
        app = application as MainApp



        if(intent.hasExtra("workout_edit")){
            edit = true
            workout = intent.extras?.getParcelable("workout_edit")!!
            //Set already stored values in text-fields
            binding.workoutTitle.setText(workout.title)
            //todo - list choosen exercises
        }

        binding.btnSave.setOnClickListener() {
            workout.title = binding.workoutTitle.text.toString()
            //todo - copy choosen exercises

            if (workout.title.isNotEmpty()) {
                if (edit) {
                    app.workouts.update(workout.copy())
                }

                else {
                    app.workouts.create(workout.copy())
                }

                //finish activity- to end intend, which is created over an other activity
                setResult(RESULT_OK)
                finish()
            }

            else{
                Snackbar
                    .make(it,R.string.manage_workout_snack_infosMissing, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }


    /******************Menu bar*******************/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity, menu)
        if(edit)menu.getItem(1).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_delete -> {
                app.workouts.delete(workout)
                setResult(99)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}