package org.vollol.ourworkout.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import org.vollol.ourworkout.R
import org.vollol.ourworkout.adapters.WorkoutAdapter
import org.vollol.ourworkout.adapters.WorkoutListener
import org.vollol.ourworkout.databinding.ActivityManageWorkoutListBinding
import org.vollol.ourworkout.main.MainApp
import org.vollol.ourworkout.models.Workout

class ManageWorkoutListActivity : AppCompatActivity(), WorkoutListener {

    private lateinit var binding: ActivityManageWorkoutListBinding

    lateinit var app: MainApp
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //including the menu-toolbar
        binding.toolbar.setTitle(R.string.manage_workout_list_activity_toolbar_title)
        setSupportActionBar(binding.toolbar)

        //refer to MainApp object
        app = application as MainApp

        //include recyclerview
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = WorkoutAdapter(app.workouts.findAll(),this)


    }

    /******************Recycler view*******************/

    override fun onWorkoutClick(workout: Workout, pos:Int) {
        val launcherIntent = Intent(this, ManageWorkoutActivity::class.java)
        launcherIntent.putExtra("workout_edit", workout)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerView.adapter)?.
            notifyItemRangeChanged(0, app.workouts.findAll().size)
        }
        else {//deleting
            if(it.resultCode==99) (binding.recyclerView.adapter)?.notifyItemRemoved(position)
        }
    }


    /******************Menu bar*******************/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //check if the id is equal to the item_add, defined in menu_workout_list_activity.xml
        when(item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this,
                    ManageWorkoutActivity::class.java)
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
                notifyItemRangeChanged(0, app.workouts.findAll().size)
            }
        }
}

