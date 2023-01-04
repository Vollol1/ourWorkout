package org.vollol.ourworkout.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.vollol.ourworkout.databinding.CardExerciseBinding
import org.vollol.ourworkout.models.ExerciseModel

/*
This interface will represent click events on the exercise Card,
and allow us to abstract the response to this event.
 */
interface ExerciseListener {
    fun onExerciseClick(exercise: ExerciseModel, position: Int)
}

class ExerciseAdapter constructor(private var exersices: List<ExerciseModel>,
                                  private val listener: ExerciseListener):
    RecyclerView.Adapter<ExerciseAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder{
        val binding = CardExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val exercise = exersices[holder.adapterPosition]
        holder.bind(exercise, listener)
    }

    override fun getItemCount(): Int = exersices.size

    class MainHolder(private val binding: CardExerciseBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(exercise: ExerciseModel, listener: ExerciseListener){
            binding.exerciseTitle.text = exercise.title
            binding.exerciseName.text = exercise.name
            binding.root.setOnClickListener {listener.onExerciseClick(exercise, adapterPosition)}
        }
    }
}

