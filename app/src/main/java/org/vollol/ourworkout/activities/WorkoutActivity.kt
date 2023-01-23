package org.vollol.ourworkout.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import org.vollol.ourworkout.R
import org.vollol.ourworkout.adapters.ExerciseViewPagerAdapter
import org.vollol.ourworkout.databinding.ActivityWorkoutBinding
import org.vollol.ourworkout.models.Workout
import timber.log.Timber.i

class WorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutBinding

    private var workout = Workout()
    private var doWorkout = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflate layout
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //including the menu-toolbar
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        //check intent, so who is calling the activity, and extract given workout
        if(intent.hasExtra("workout_do")) {
            doWorkout = true
            workout = intent.extras?.getParcelable("workout_do")!!
        }

        else if(intent.hasExtra("workout_show")){
            doWorkout = false
            workout = intent.extras?.getParcelable("workout_show")!!
        }

        i("Workoutactivity started with workout: ${workout.title}")

        //todo implement choosing workout.enduranceRounds in ManageWorkoutActivity
        workout.enduranceRounds = 3

        val adapter = ExerciseViewPagerAdapter(
            workout.strengthExercises,workout.enduranceExercises, workout.enduranceRounds,
            resources.getStringArray(R.array.exercise_activity_units))
        binding.viewPager2.adapter = adapter

        binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL

        binding.indicator.setViewPager(binding.viewPager2)

        /*
        binding.viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            // This method will be invoked when a new page becomes selected. Animation is not necessarily complete.
            // position – Position index of the new selected page.
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

            }
        })
        */
    }
}