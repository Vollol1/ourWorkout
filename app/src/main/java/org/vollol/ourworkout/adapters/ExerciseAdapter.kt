package org.vollol.ourworkout.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.vollol.ourworkout.R
import org.vollol.ourworkout.databinding.CardExerciseListBinding
import org.vollol.ourworkout.databinding.ExercisePageBinding
import org.vollol.ourworkout.models.Exercise
import timber.log.Timber.i

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
class ExerciseViewPagerAdapter(private var strengthExercises: List<Exercise>,
                               var enduranceExercises: List<Exercise>,
                               var enduranceRounds : Int,
                               var units: Array<String>) :
    RecyclerView.Adapter<ExerciseViewPagerAdapter.Pager2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder{
        val binding = ExercisePageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return Pager2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        val exercise: Exercise
        val numOfStrExercises = strengthExercises.size
        val numOfEndExercises = enduranceExercises.size
        var exerciseIndex = position
        var actRound: Int = position
        val isEndurance: Boolean
        //choose between strength and endurance Exercises
        if (position < numOfStrExercises) {
            exercise = strengthExercises[exerciseIndex]
            isEndurance = false
        } else {
            exerciseIndex = (position - numOfStrExercises) % numOfEndExercises
            exercise = enduranceExercises[exerciseIndex]

            //calculate actual endurance Round Number
            actRound = ((position - numOfStrExercises) / numOfEndExercises)
            actRound++
            isEndurance = true
        }
        i("adapt.Pos: $position, ex.Index: $exerciseIndex, isEndurance: $isEndurance, roundNr: $actRound, ex.Title:${exercise.title}")
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
                binding.textRounds.text = "Round $enduranceRound/$enduranceRounds"
                binding.editRoundNumber.visibility = View.INVISIBLE
            }
            else{
                binding.textRounds.setText(R.string.workout_activity_text_exerciseRounds)
                binding.editRoundNumber.visibility = View.VISIBLE
                binding.editRoundNumber.setText(exercise.rounds.toString())
            }

            //if unit is calories Calories -> no repetitions needed
            if(exercise.unit == units[0]){
                binding.layoutRepetitions.visibility = View.INVISIBLE
            }
            else{
                binding.layoutRepetitions.visibility = View.VISIBLE
                //layoutRepetitions
                binding.editRepetitionNumber.setText(exercise.repsPerRound.toString())
            }

            //layoutOntime, layoutOffTime, layoutRoundDuration
            if(isEndurance){
                binding.layoutOnTime.visibility = View.INVISIBLE
                binding.layoutOffTime.visibility = View.INVISIBLE
                binding.layoutRoundDuration.visibility = View.INVISIBLE
            }
            else{
                binding.layoutOnTime.visibility = View.VISIBLE
                binding.layoutOffTime.visibility = View.VISIBLE
                binding.layoutRoundDuration.visibility = View.VISIBLE
                //layoutOnTime
                binding.editOnTimeNumber.setText(exercise.onTime.toString())
                //layoutOffTime
                binding.editOffTimeNumber.setText(exercise.offTime.toString())
                //layoutRoundDuration
                binding.editRoundDurationNumber.setText(exercise.roundDuration.toString())
            }

            //layoutUnit
            when(exercise.unit){
                //Calories
                units[0] -> {
                    binding.layoutUnit.visibility = View.VISIBLE
                    binding.textUnit.text = units[0]
                    binding.editUnitNumber.setText(exercise.calories.toString())
                }
                //Kg
                units[1] -> {
                    binding.layoutUnit.visibility = View.VISIBLE
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