package org.vollol.ourworkout.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.vollol.ourworkout.databinding.ActivityCockpitBinding

class CockpitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCockpitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflate layout
        binding = ActivityCockpitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //handle button navExercise -> set callback
        binding.btnNavExercise.setOnClickListener() {
            val launcherIntent = Intent(this,ExerciseListActivity::class.java)
            startActivity(launcherIntent)
        }
    }

}