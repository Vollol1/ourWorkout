package org.vollol.ourworkout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.vollol.ourworkout.databinding.CardWorkoutBinding
import org.vollol.ourworkout.models.Workout

/*
This interface will represent click events on the workout Card,
and allow us to abstract the response to this event.
 */
interface WorkoutListener {
    fun onWorkoutClick(workout: Workout, position: Int)
}

class WorkoutAdapter constructor(private var workouts: List<Workout>,
                                  private val listener: WorkoutListener):
    RecyclerView.Adapter<WorkoutAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder{
        val binding = CardWorkoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val workout = workouts[holder.adapterPosition]
        holder.bind(workout, listener)
    }

    override fun getItemCount(): Int = workouts.size

    class MainHolder(private val binding: CardWorkoutBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(workout: Workout, listener: WorkoutListener){
            binding.workoutTitle.text = workout.title
            binding.root.setOnClickListener {listener.onWorkoutClick(workout, adapterPosition)}
        }
    }
}

