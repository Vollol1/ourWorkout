package org.vollol.ourworkout.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
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

        //check intent, so who is calling the activity
        if(intent.hasExtra("workout_do")) {
            doWorkout = true
            workout = intent.extras?.getParcelable("workout_do")!!
        }

        else if(intent.hasExtra("workout_show")){
            doWorkout = false
            workout = intent.extras?.getParcelable("workout_show")!!
        }

        i("folgendes Workout wurde übergeben: ${workout.title}")

        //todo implement choosing workout.enduranceRounds in ManageWorkoutActivity
        workout.enduranceRounds = 3

        binding.viewPager2.adapter = ExerciseViewPagerAdapter(
            workout.strengthExercises,workout.enduranceExercises, workout.enduranceRounds, resources.getStringArray(R.array.exercise_activity_units))
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.indicator.setViewPager(binding.viewPager2)
    }
}