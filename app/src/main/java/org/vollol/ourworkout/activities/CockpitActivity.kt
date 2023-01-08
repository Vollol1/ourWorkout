package org.vollol.ourworkout.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.vollol.ourworkout.adapters.WorkoutAdapter
import org.vollol.ourworkout.adapters.WorkoutListener
import org.vollol.ourworkout.databinding.ActivityCockpitBinding
import org.vollol.ourworkout.main.MainApp
import org.vollol.ourworkout.models.Workout

class CockpitActivity : AppCompatActivity(), WorkoutListener {

    private lateinit var binding: ActivityCockpitBinding

    lateinit var app: MainApp
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflate layout
        binding = ActivityCockpitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //including the menu-toolbar
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        //refer to MainApp object
        app = application as MainApp

        //include recyclerview
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerViewDoWorkout.layoutManager = layoutManager
        binding.recyclerViewDoWorkout.adapter = WorkoutAdapter(app.workouts.findAll(),this)

        //handle button btnNavManageWorkouts -> set callback
        binding.btnNavManageWorkouts.setOnClickListener() {
            val launcherIntent = Intent(this,ManageWorkoutListActivity::class.java)
            startActivity(launcherIntent)
        }

        //handle button btnNavManageExercises -> set callback
        binding.btnNavManageExercises.setOnClickListener() {
            val launcherIntent = Intent(this,ExerciseListActivity::class.java)
            startActivity(launcherIntent)
        }

        //handle button bntShowResults -> set callback
        binding.btnNavShowResults.setOnClickListener() {
            val launcherIntent = Intent(this,ShowDoneWorkoutsActivity::class.java)
            startActivity(launcherIntent)
        }
    }

    /***************************Reload Recyclerview*****************************/
    override fun onResume() {
        super.onResume()

        (binding.recyclerViewDoWorkout.adapter)?.notifyItemRemoved(position)
        //reload recyclerview
        (binding.recyclerViewDoWorkout.adapter)?.
        notifyItemRangeChanged(0, app.workouts.findAll().size)

        //both methods are called, it works, i don't know why, but if an item is removed, it will also
        //be not shown in the cockpit anymore


    }

    /******************Recycler view*******************/

    override fun onWorkoutClick(workout: Workout, pos:Int) {
        val launcherIntent = Intent(this, WorkoutActivity::class.java)
        launcherIntent.putExtra("workout_do", workout)
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