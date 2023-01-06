package org.vollol.ourworkout.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.vollol.ourworkout.databinding.ActivityCockpitBinding
import org.vollol.ourworkout.main.MainApp

class CockpitActivity : AppCompatActivity() {

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

}