package org.vollol.ourworkout.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager

import org.vollol.ourworkout.adapters.DoAbleWorkoutAdapter
import org.vollol.ourworkout.adapters.DoAbleWorkoutListener
import org.vollol.ourworkout.databinding.ActivityShowDoneWorkoutsBinding
import org.vollol.ourworkout.main.MainApp
import org.vollol.ourworkout.models.DoAbleWorkout
import org.vollol.ourworkout.models.Workout

class ShowDoneWorkoutsActivity : AppCompatActivity(), DoAbleWorkoutListener {
    private lateinit var binding: ActivityShowDoneWorkoutsBinding

    lateinit var app: MainApp
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflate layout
        binding = ActivityShowDoneWorkoutsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //including the menu-toolbar
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        //refer to MainApp object
        app = application as MainApp

        //include recyclerview
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewDoWorkout.layoutManager = layoutManager
        binding.recyclerViewDoWorkout.adapter = DoAbleWorkoutAdapter(app.doneWorkouts.findAll(), this)
    }

    /******************Recycler view*******************/

    override fun onWorkoutClick(workout: DoAbleWorkout, pos:Int) {
        val launcherIntent = Intent(this, WorkoutActivity::class.java)
        launcherIntent.putExtra("workout_show", workout)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerViewDoWorkout.adapter)?.
            notifyItemRangeChanged(0, app.workouts.findAll().size)
        }
        else {//deleting
            if(it.resultCode==99) (binding.recyclerViewDoWorkout.adapter)?.notifyItemRemoved(position)
        }
    }

}