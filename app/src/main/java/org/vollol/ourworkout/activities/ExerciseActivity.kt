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
import org.vollol.ourworkout.main.MainApp
import org.vollol.ourworkout.models.Exercise
import timber.log.Timber.i

class ExerciseActivity : AppCompatActivity(){

    /* ActivityExerciseBinding - this is an auto generated class, which allows to have access on all
    *  content defined in the according layout description (.xml-file)
    *  In this case -> activity_exercise.xml
    */
    private lateinit var binding: ActivityExerciseBinding

    lateinit var app: MainApp

    var exercise = Exercise()
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflate Layout to the screen, which is defined in the .xml file
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //including the menu-toolbar
        binding.toolbarCancel.title = getString(R.string.exercise_activity_title)
        setSupportActionBar(binding.toolbarCancel)

        //initialize app from instantiated MainApp-Class
        app = application as MainApp

        /***********************************spinner********************************************/
        val spinner = findViewById<Spinner>(R.id.exerciseUnit)
        //ArrayAdapter, which shows strings out of a string-array
        val units = resources.getStringArray(R.array.exercise_activity_units)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        //Set dropdown-layout for the spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //set adapter for the spinner
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position).toString()
                i("Spinner: $item")
                exercise.unit = item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // is called, if nothing is selected - e.g. at the start
            }
        }
        /*********************************spinner-end******************************************/

        if(intent.hasExtra("exercise_edit")){
            edit = true
            exercise = intent.extras?.getParcelable("exercise_edit")!!
            //Set already stored values in text-fields
            binding.exerciseTitle.setText(exercise.title)
            binding.exerciseName.setText(exercise.name)
            binding.exerciseDesc.setText(exercise.desc)
            //Set already stored values in the spinner
            val position = adapter.getPosition(exercise.unit)
            spinner.setSelection(position)

            //change text on button to save exercise
            binding.btnAdd.setText(R.string.exercise_activity_button_saveExercise)
        }

        binding.btnAdd.setOnClickListener() {
            exercise.name = binding.exerciseName.text.toString()
            exercise.title = binding.exerciseTitle.text.toString()
            exercise.desc = binding.exerciseDesc.text.toString()

            if (exercise.title.isNotEmpty()) {
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
                    .make(it,R.string.exercise_activity_snack_infosMissing, Snackbar.LENGTH_LONG)
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