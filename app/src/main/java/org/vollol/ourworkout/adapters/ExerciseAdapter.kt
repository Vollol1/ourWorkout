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




//For implementation have a  look at https://www.youtube.com/watch?v=lAckLFH7mIE&t=292s
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
class ExerciseViewPagerAdapter(var exercises: List<Exercise>, var units: Array<String>, var editable: Boolean) :
    RecyclerView.Adapter<ExerciseViewPagerAdapter.Pager2ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder{
        val binding = ExercisePageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return Pager2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        holder.bind(exercises[holder.adapterPosition], editable)
    }

    override fun getItemCount() : Int{
        return exercises.size
    }

    inner class Pager2ViewHolder(private val binding: ExercisePageBinding) :
        RecyclerView.ViewHolder(binding.root){

        fun bind(exercise: Exercise, editable: Boolean) {
            //Info text
            binding.exerciseTitle.text = exercise.title
            binding.exerciseDesc.text = exercise.desc
            binding.exerciseName.text = exercise.name

            /********************layoutRounds********************/
            if(editable) {
                binding.editRoundNumber.isEnabled = true
                binding.editRoundNumber.setOnClickListener() {
                    exercise.rounds = binding.editRoundNumber.text.toString().toInt()
                }
            }
            else{
                binding.editRoundNumber.isEnabled = false
            }

            if(exercise.isEndurance){
                binding.textRounds.text = "Round ${exercise.round}"
                binding.editRoundNumber.visibility = View.INVISIBLE
            }
            else{
                binding.editRoundNumber.visibility = View.VISIBLE
                binding.textRounds.setText(R.string.workout_activity_text_exerciseRounds)
                binding.editRoundNumber.setText(exercise.rounds.toString())
            }

            /********************layoutRepetitions********************/
            if(editable) {
                binding.editRepetitionNumber.isEnabled = true
                binding.editRepetitionNumber.setOnClickListener() {
                    exercise.repsPerRound = binding.editRepetitionNumber.text.toString().toInt()
                }
            }
            else{
                binding.editRepetitionNumber.isEnabled = false
            }

            if(exercise.unit == units[0]){
                binding.layoutRepetitions.visibility = View.INVISIBLE
            }
            else{
                binding.layoutRepetitions.visibility = View.VISIBLE
                binding.editRepetitionNumber.setText(exercise.repsPerRound.toString())
            }

            /********************layoutOnTime********************/
            if(editable) {
                binding.editOnTimeNumber.isEnabled = true
                binding.editOnTimeNumber.setOnClickListener(){
                    exercise.onTime = binding.editOnTimeNumber.text.toString().toInt()
                }
            }
            else{
                binding.editOnTimeNumber.isEnabled = false
            }

            if(exercise.isEndurance) {
                binding.layoutOnTime.visibility = View.INVISIBLE
            }
            else{
                binding.layoutOnTime.visibility = View.VISIBLE
                binding.editOnTimeNumber.setText(exercise.onTime.toString())
            }

            /********************layoutOffTime********************/
            if(editable) {
                binding.editOffTimeNumber.isEnabled = true
                binding.editOffTimeNumber.setOnClickListener(){
                    exercise.offTime = binding.editOffTimeNumber.text.toString().toInt()
                }
            }
            else{
                binding.editOffTimeNumber.isEnabled = false
            }

            if(exercise.isEndurance) {
                binding.layoutOffTime.visibility = View.INVISIBLE
            }
            else{
                binding.layoutOffTime.visibility = View.VISIBLE
                binding.editOffTimeNumber.setText(exercise.offTime.toString())
            }

            /********************layoutRoundDuration********************/
            if(editable) {
                binding.editRoundDurationNumber.isEnabled = true
                binding.editRoundDurationNumber.setOnClickListener(){
                    exercise.roundDuration = binding.editRoundDurationNumber.text.toString().toInt()
                }
            }
            else{
                binding.editRoundDurationNumber.isEnabled = false
            }

            if(exercise.isEndurance) {
                binding.layoutRoundDuration.visibility = View.INVISIBLE
            }
            else{
                binding.layoutRoundDuration.visibility = View.VISIBLE
                binding.editRoundDurationNumber.setText(exercise.roundDuration.toString())
            }

            /********************layoutUnit********************/
            if(editable) {
                binding.editUnitNumber.isEnabled = true
                binding.editUnitNumber.setOnClickListener() {
                    exercise.unitVal = binding.editUnitNumber.text.toString().toDouble()
                }
            }
            else{
                binding.editUnitNumber.isEnabled = false
            }

            if (exercise.unit == units[2]){
                binding.layoutUnit.visibility = View.INVISIBLE
            }
            else{
                binding.layoutUnit.visibility = View.VISIBLE
                binding.textUnit.text = exercise.unit
                binding.editUnitNumber.setText(exercise.unitVal.toString())
            }

        }
    }
}