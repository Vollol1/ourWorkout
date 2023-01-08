package org.vollol.ourworkout.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.vollol.ourworkout.R
import org.vollol.ourworkout.adapters.ExerciseRecyclerViewAdapter
import org.vollol.ourworkout.adapters.ExerciseSpinnerAdapter
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

    var selectedStrengthExercise = Exercise()
    var selectedEnduranceExercise = Exercise()

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

        /***********************************recycler views***********************************/
        val layoutManagerStrength = LinearLayoutManager(this)
        val layoutManagerEndurance = LinearLayoutManager(this)

        binding.recyclerViewStrengthExercise.layoutManager = layoutManagerStrength
        binding.recyclerViewStrengthExercise.adapter = ExerciseRecyclerViewAdapter(workout.strengthExercises as List<Exercise>)

        binding.recyclerViewEnduranceExercise.layoutManager = layoutManagerEndurance
        binding.recyclerViewEnduranceExercise.adapter = ExerciseRecyclerViewAdapter(workout.enduranceExercises as List<Exercise>)

        /***********************************spinners********************************************/
        val strengthSpinner = findViewById<Spinner>(R.id.strengthExerciseSpinner)
        val enduranceSpinner = findViewById<Spinner>(R.id.enduranceExerciseSpinner)

        //ArrayAdapter, which shows strings out of a string-array
        val itemsExercises = app.exercises.findAll()
        val adapterSpinnerStrengthExercise = ExerciseSpinnerAdapter(this, itemsExercises)
        val adapterSpinnerEnduranceExercise = ExerciseSpinnerAdapter(this, itemsExercises)

        //Set dropdown-layout for the spinner
        adapterSpinnerStrengthExercise.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterSpinnerEnduranceExercise.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //set adapter for the spinner
        strengthSpinner.adapter = adapterSpinnerStrengthExercise
        enduranceSpinner.adapter = adapterSpinnerEnduranceExercise

        /**********************Strength Exercise spinner************************/
        strengthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position)
                i("Spinner selected: $item")
                selectedStrengthExercise = item as Exercise
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // is called, if nothing is selected - e.g. at the start
            }
        }

        /**********************Endurance Exercise spinner************************/
        enduranceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent?.getItemAtPosition(position)
                i("Spinner selected: $item")
                selectedEnduranceExercise = item as Exercise
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // is called, if nothing is selected - e.g. at the start
            }
        }
        /*********************************spinners-end******************************************/

        binding.btnAddStrengthExercise.setOnClickListener() {
            if(selectedStrengthExercise.title != "") {
                workout.strengthExercises.add(selectedStrengthExercise)
                //notify adapter that items have been changed
                (binding.recyclerViewStrengthExercise.adapter)?.notifyDataSetChanged()
            }
            else{
                Snackbar
                    .make(it,R.string.manage_workout_activity_snack_noExerciseAvailable, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.btnAddEnduranceExercise.setOnClickListener() {
            if(selectedEnduranceExercise.title != "") {
                workout.enduranceExercises.add(selectedEnduranceExercise)
                //notify adapter that items have been changed
                (binding.recyclerViewEnduranceExercise.adapter)?.notifyDataSetChanged()
            }
            else{
                Snackbar
                    .make(it,R.string.manage_workout_activity_snack_noExerciseAvailable, Snackbar.LENGTH_LONG)
                    .show()
            }
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
                    .make(it,R.string.manage_workout_activity_snack_infoTitelMissing, Snackbar.LENGTH_LONG)
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