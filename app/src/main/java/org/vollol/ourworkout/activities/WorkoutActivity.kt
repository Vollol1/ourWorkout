package org.vollol.ourworkout.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import org.vollol.ourworkout.adapters.ExerciseCardAdapter
import org.vollol.ourworkout.databinding.ActivityWorkoutBinding
import org.vollol.ourworkout.models.Workout
import timber.log.Timber.i

class WorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutBinding
    private lateinit var adapter: PagerAdapter

    private var workout = Workout()
    var doWorkout = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflate layout
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //including the menu-toolbar
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        //check intent, so who is calling the activity
        if(intent.hasExtra("workout_do")) {
            doWorkout = true
            workout = intent.extras?.getParcelable("workout_do")!!
        }

        else if(intent.hasExtra("workout_show")){
            doWorkout = false
            workout = intent.extras?.getParcelable("workout_show")!!
        }

        i("folgendes Workout wurde übergeben: ${workout.title.toString()}")

        //instantiate adapter
        adapter = ExerciseCardAdapter(this, workout.strengthExercises, workout.enduranceExercises)
        binding.viewPager.adapter = adapter
        binding.viewPager.setPadding(100, 0, 100, 0)


        //add page change listener
        binding.viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val title = workout.strengthExercises[position].title
                binding.toolbar.title = title
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }
}