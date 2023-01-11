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
class ExerciseViewPagerAdapter(private var strengthExercises: List<Exercise>, var enduranceExercises: List<Exercise>,var enduranceRounds : Int, var units: Array<String>) :
    RecyclerView.Adapter<ExerciseViewPagerAdapter.Pager2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder{
        val binding = ExercisePageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return Pager2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        val exercise : Exercise
        val numOfStrExercises = strengthExercises.size
        val numOfEndExercises = enduranceExercises.size
        val pos = holder.adapterPosition
        var actRound : Int = pos
        val isEndurance : Boolean
        //choose between strength and endurance Exercises
        if(pos < numOfStrExercises) {
            exercise = strengthExercises[pos]
            isEndurance = false
        }
        else{
            exercise = enduranceExercises[(pos-numOfStrExercises)%numOfEndExercises]
            //calculate actual endurance Round Number
            actRound  = ((pos-numOfStrExercises)/numOfEndExercises)
            actRound++
            isEndurance = true
        }
        holder.bind(exercise, isEndurance, actRound)
    }

    override fun getItemCount() : Int{
        var numOfItems : Int = strengthExercises.size
        //every single round of endurance exercises will be shown extra
        numOfItems += (enduranceExercises.size * enduranceRounds)
        return numOfItems
    }

    inner class Pager2ViewHolder(private val binding: ExercisePageBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(exercise: Exercise, isEndurance: Boolean, enduranceRound: Int) {
            //Info text
            binding.exerciseTitle.text = exercise.title
            binding.exerciseDesc.text = exercise.desc
            binding.exerciseName.text = exercise.name

            //layoutRounds
            if(isEndurance){
                binding.textRounds.setText("Round")
            }
            else {
                binding.editRoundNumber.setText(exercise.rounds.toString())
            }

            //layoutRepetitions
            binding.editRepetitionNumber.setText(exercise.repsPerRound.toString())

            //layoutOnTime
            binding.editOnTimeNumber.setText(exercise.onTime.toString())

            //layoutOffTime
            binding.editOffTimeNumber.setText(exercise.offTime.toString())

            //layoutRoundDuration
            binding.editRoundDurationNumber.setText(exercise.roundDuration.toString())

            //layoutUnit
            when(exercise.unit){
                //Calories
                units[0] -> {
                    binding.textUnit.text = units[0]
                    binding.editUnitNumber.setText(exercise.calories.toString())
                }
                //Kg
                units[1] -> {
                    binding.textUnit.text = units[1]
                    binding.editUnitNumber.setText(exercise.weight.toString())
                }
                //None or not defined -> do not show field at all
                else -> {
                    binding.layoutUnit.visibility = View.INVISIBLE
                }
            }


        }
    }

}