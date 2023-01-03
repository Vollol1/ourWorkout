package org.vollol.ourworkout.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.vollol.ourworkout.R
import org.vollol.ourworkout.adapters.ExerciseAdapter
import org.vollol.ourworkout.databinding.ActivityExerciseListBinding
import org.vollol.ourworkout.databinding.CardExerciseBinding
import org.vollol.ourworkout.main.MainApp
import org.vollol.ourworkout.models.ExerciseModel

class ExerciseListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseListBinding

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //including the menu-toolbar
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        //refer to MainApp object
        app = application as MainApp

        //include recyclerview
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ExerciseAdapter(app.exercises)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_exercise_list_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //check if the id is equal to the item_add, defined in menu_exercise_list_activity.xml
        when(item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this,
                    ExerciseActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if(it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0, app.exercises.size)
            }
        }
}

