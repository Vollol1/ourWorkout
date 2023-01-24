package org.vollol.ourworkout.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import org.vollol.ourworkout.R
import org.vollol.ourworkout.adapters.ExerciseViewPagerAdapter
import org.vollol.ourworkout.databinding.ActivityWorkoutBinding
import org.vollol.ourworkout.databinding.ExercisePageBinding
import org.vollol.ourworkout.main.MainApp
import org.vollol.ourworkout.models.DoAbleWorkout
import org.vollol.ourworkout.models.Workout
import timber.log.Timber.i
import java.time.LocalDateTime

class WorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutBinding

    lateinit var app: MainApp

    private var workout = Workout()
    private var workoutToDo = DoAbleWorkout()
    private var doWorkout = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflate layout
        binding = ActivityWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //including the menu-toolbar
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        //initialize app from instantiated MainApp-Class
        app = application as MainApp

        //check intent, so who is calling the activity, and extract given workout
        if(intent.hasExtra("workout_do")) { //given type is Workout
            doWorkout = true
            workout = intent.extras?.getParcelable("workout_do")!!

            //generate DoAbleWorkout from given Workout-instance
            workoutToDo.title = workout.title
            workoutToDo.enduranceRounds = workout.enduranceRounds
            workoutToDo.enduranceDuration = workout.enduranceDuration
            workoutToDo.strengthDuration = workout.strengthDuration
            workoutToDo.timeStamp = LocalDateTime.now()

            //add strength exercises to exercise list
            workoutToDo.exercises.addAll(workout.strengthExercises.toMutableList())

            //add endurance exercises to exercise list
            for(round in 1.rangeTo(workout.enduranceRounds)){
                for(ex in workout.enduranceExercises){
                    ex.isEndurance = true
                    ex.round = round
                    workoutToDo.exercises.add(ex.copy())
                }
            }
        }

        else if(intent.hasExtra("workout_show")){ //type is DoAbleWorkout
            doWorkout = false
            workout = intent.extras?.getParcelable("workout_show")!!
        }

        i("Workoutactivity started with workout: ${workout.title}")


        val adapter = ExerciseViewPagerAdapter(workoutToDo.exercises, resources.getStringArray(R.array.exercise_activity_units))
        binding.viewPager2.adapter = adapter

        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.indicator.setViewPager(binding.viewPager2)
    }

    override fun onPause() {
        super.onPause()
        //store new doneWorkout in global storage
        if(doWorkout){
            app.doneWorkouts.create(workoutToDo)
        }
    }
}