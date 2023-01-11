package org.vollol.ourworkout.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.vollol.ourworkout.R
import org.vollol.ourworkout.databinding.CardExerciseListBinding
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

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val exercise = getItem(position)
        val textView = view as TextView
        if (exercise != null) {
            textView.text = exercise.title + " - " + exercise.unit
        }
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val exercise = getItem(position)
        val textView = view as TextView
        if (exercise != null) {
            textView.text = exercise.title + " - " + exercise.unit
        }
        return view
    }
}

//For implementation have a  look at https://www.youtube.com/watch?v=xlonlt5fAzg

class ExerciseViewPagerAdapter(private val exercise: List<Exercise>) : RecyclerView.Adapter<ExerciseViewPagerAdapter.Pager2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewPagerAdapter.Pager2ViewHolder{
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.exercise_page, parent, false))
    }

    override fun getItemCount(): Int {
        return exercise.size
    }

    override fun onBindViewHolder(holder: ExerciseViewPagerAdapter.Pager2ViewHolder, position: Int) {
        holder.exerciseTitle.text = exercise[position].title
        holder.exerciseDesc.text = exercise[position].desc
    }

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseTitle: TextView = itemView.findViewById(R.id.exerciseTitle)
        val exerciseDesc: TextView = itemView.findViewById(R.id.exerciseDesc)

        init {
            exerciseTitle.setOnClickListener{v: View ->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked on item #${position +1}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}