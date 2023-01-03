package org.vollol.ourworkout.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.vollol.ourworkout.R
import org.vollol.ourworkout.databinding.ActivityExerciseBinding
import org.vollol.ourworkout.main.MainApp
import org.vollol.ourworkout.models.ExerciseModel


class ExerciseActivity : AppCompatActivity() {

    /* ActivityExerciseBinding - this is an auto generated class, which allows to have access on all
    *  content defined in the according layout description (.xml-file)
    *  In this case -> activity_exercise.xml
    */
    private lateinit var binding: ActivityExerciseBinding

    lateinit var app: MainApp

    var exercise = ExerciseModel()
    var edit = false

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

        if(intent.hasExtra("exercise_edit")){
            edit = true
            exercise = intent.extras?.getParcelable("exercise_edit")!!
            binding.exerciseTitle.setText(exercise.title)
            binding.exerciseName.setText(exercise.name)

            //change text on button to save exercise
            binding.btnAdd.setText(R.string.button_saveExercise)
        }

        binding.btnAdd.setOnClickListener() {
            exercise.name = binding.exerciseName.text.toString()
            exercise.title = binding.exerciseTitle.text.toString()

            if (exercise.title.isNotEmpty() and exercise.name.isNotEmpty()) {
                if (edit) {
                    app.exercises.update(exercise.copy())
                }

                else {
                    app.exercises.create(exercise.copy())
                }

                //finish activity- to end intend, which is created over an other activity
                setResult(RESULT_OK)
                finish()
            }

            else{
                Snackbar
                    .make(it,R.string.snack_infosMissing, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_exercise_activity, menu)
        if(edit)menu.getItem(1).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_delete -> {
                app.exercises.delete(exercise)
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