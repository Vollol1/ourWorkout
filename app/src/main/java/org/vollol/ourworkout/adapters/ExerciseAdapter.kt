package org.vollol.ourworkout.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.vollol.ourworkout.databinding.CardExerciseListBinding
import org.vollol.ourworkout.databinding.ExercisePageBinding
import org.vollol.ourworkout.models.Exercise

/*
This interface will represent click events on the exercise Card,
and allow us to abstract the response to this event.
 */
interface ExerciseListener {
    fun onExerciseClick(exercise: Exercise, position: Int)
}

class ExerciseRecyclerViewAdapter(private var exersices: List<Exercise>,
                                  private val listener: ExerciseListener? = null):
    RecyclerView.Adapter<ExerciseRecyclerViewAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder{
        val binding = CardExerciseListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val exercise = exersices[holder.adapterPosition]
        if (listener != null) {
            holder.bind(exercise, listener)
        }
        else {
            holder.bind(exercise)
        }
    }

    override fun getItemCount(): Int = exersices.size

    class MainHolder(private val binding: CardExerciseListBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(exercise: Exercise, listener: ExerciseListener? = null) {
            binding.exerciseTitle.text = exercise.title
            binding.exerciseUnit.text = exercise.unit
            if (listener != null) {
                binding.exerciseDesc.text = exercise.desc
                binding.root.setOnClickListener {
                    listener.onExerciseClick(
                        exercise,
                        adapterPosition
                    )
                }
            }
        }
    }
}

//For implementation have a  look at https://www.youtube.com/watch?v=MeG-0MVP3jw

class ExerciseSpinnerAdapter(context: Context, exercises: List<Exercise>) : ArrayAdapter<Exercise>(context, android.R.layout.simple_spinner_item, exercises) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val exercise = getItem(position)
        val textView = view as TextView
        if (exercise != null) {
            "${exercise.title} - ${exercise.unit}".also { textView.text = it }
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val exercise = getItem(position)
        val textView = view as TextView
        if (exercise != null) {
            "${exercise.title} - ${exercise.unit}".also { textView.text = it }
        }
        return view
    }
}

//For implementation have a  look at https://www.youtube.com/watch?v=xlonlt5fAzg

class ExerciseViewPagerAdapter(private var exercises: List<Exercise>) :
    RecyclerView.Adapter<ExerciseViewPagerAdapter.Pager2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder{
        val binding = ExercisePageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return Pager2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        val exercise = exercises[holder.adapterPosition]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exercises.size

    class Pager2ViewHolder(private val binding: ExercisePageBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(exercise: Exercise) {
            binding.exerciseTitle.text = exercise.title
            binding.exerciseDesc.text = exercise.desc
        }
    }

}