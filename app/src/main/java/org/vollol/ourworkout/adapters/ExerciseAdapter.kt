package org.vollol.ourworkout.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import org.vollol.ourworkout.databinding.CardExerciseBinding
import org.vollol.ourworkout.databinding.CardExerciseListBinding
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
            textView.text = exercise.title + " - " + exercise.unit
        }
        return view
    }

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

class ExerciseCardAdapter(private val context: Context,
                          private val strengthExercises: List<Exercise>,
                          private val enduranceExercises: List<Exercise>) : PagerAdapter(){

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return strengthExercises.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        //inflate layout card_exercise
        val view = CardExerciseBinding.inflate(LayoutInflater.from(context), container, false)
        //get data
        val exercise = strengthExercises[position]

        //set data to ui views
        view.exerciseTitle.text = exercise.title
        //todo bind other items also

        //todo handle inputted numbers
        i("Card Item instantiated with exercise: ${exercise.title.toString()}")

        container.addView(view.root, position)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}
