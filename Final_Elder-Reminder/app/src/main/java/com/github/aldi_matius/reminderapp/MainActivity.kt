package com.github.aldi_matius.reminderapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.aldi_matius.reminderapp.data.Task
import com.github.aldi_matius.reminderapp.adapters.todoAdapter
import com.github.aldi_matius.reminderapp.viewmodels.TaskViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity(), SensorEventListener {
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            Log.d("Linear acceleration", p0.values[2].toString())
            if(p0.values[2] > 5) {
                if(phoneNumber != "") {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:" + phoneNumber)
                    startActivity(intent)
                    //Toast.makeText(it.context, contact, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val ADD_TASK_REQUEST = 1
        const val EDIT_TASK_REQUEST = 2
    }

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var sensorManager: SensorManager
    private lateinit var linearAcceleration: Sensor
    private var phoneNumber = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        this.sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)?.let {
            this.linearAcceleration = it
        }
        sensorManager.registerListener(this, this.linearAcceleration, SensorManager.SENSOR_DELAY_NORMAL)

        buttonAddTask.setOnClickListener {
            startActivityForResult(
                Intent(this, AddEditTaskActivity::class.java),
                ADD_TASK_REQUEST
            )
        }
        emergency_call.setOnClickListener {
            if(phoneNumber != "") {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:" + phoneNumber)
                startActivity(intent)
                //Toast.makeText(it.context, contact, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(it.context, "Phone number not set yet!", Toast.LENGTH_SHORT).show()
            }

        }

        emergency_set.setOnClickListener {
            val i = Intent(applicationContext, SetContactActivity::class.java)
            startActivity(i)
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        val adapter = todoAdapter()

        recycler_view.adapter = adapter

        taskViewModel.getAllTask().observe(this, Observer<List<Task>> {
            adapter.submitList(it)
        })

        adapter.setOnItemClickListener(object : todoAdapter.OnItemClickListener {
            override fun onItemClick(task: Task) {
                val intent = Intent(baseContext, AddEditTaskActivity::class.java)
                intent.putExtra(AddEditTaskActivity.EXTRA_ID, task.id)
                intent.putExtra(AddEditTaskActivity.EXTRA_TITLE, task.title)
                intent.putExtra(AddEditTaskActivity.EXTRA_DESCRIPTION, task.description)
                intent.putExtra(AddEditTaskActivity.EXTRA_DUE_TIME, task.duetime)
                intent.putExtra(AddEditTaskActivity.EXTRA_DUE_DATE, task.duedate)

                startActivityForResult(intent, EDIT_TASK_REQUEST)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val file = File(applicationContext.filesDir, "phone.txt")
        if(file.exists()) {
            phoneNumber = file.readText()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_TASK_REQUEST && resultCode == Activity.RESULT_OK) {
            val newTask = Task(
                data!!.getStringExtra(AddEditTaskActivity.EXTRA_TITLE),
                data.getStringExtra(AddEditTaskActivity.EXTRA_DESCRIPTION),
                data.getStringExtra(AddEditTaskActivity.EXTRA_DUE_TIME),
                data.getStringExtra(AddEditTaskActivity.EXTRA_DUE_DATE)
            )
            taskViewModel.insert(newTask)

            Toast.makeText(this, "Task saved!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_TASK_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(AddEditTaskActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Could not update! Error!", Toast.LENGTH_SHORT).show()
            }

            val updateTask = Task(
                data!!.getStringExtra(AddEditTaskActivity.EXTRA_TITLE),
                data.getStringExtra(AddEditTaskActivity.EXTRA_DESCRIPTION),
                data.getStringExtra(AddEditTaskActivity.EXTRA_DUE_TIME),
                data.getStringExtra(AddEditTaskActivity.EXTRA_DUE_DATE)
            )
            updateTask.id = data.getIntExtra(AddEditTaskActivity.EXTRA_ID, -1)
            taskViewModel.update(updateTask)

        } else {
            Toast.makeText(this, "Task not saved!", Toast.LENGTH_SHORT).show()
        }


    }
}


