package com.github.aldi_matius.reminderapp.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class TaskRepository(application: Application) {

    private var taskDao: TaskDao

    private var allTask: LiveData<List<Task>>

    init {
        val database: TaskDatabase = TaskDatabase.getInstance(
            application.applicationContext
        )!!
        taskDao = database.taskDao()
        allTask = taskDao.getAllTask()
    }

    fun insert(task: Task) {
        InsertTaskAsyncTask(taskDao).execute(task)
    }

    fun update(task: Task) {
        UpdateTaskAsyncTask(taskDao).execute(task)
    }


    fun delete(task: Task) {
        DeleteTaskAsyncTask(taskDao).execute(task)
    }

    fun deleteID(id: Int) {
        DeleteIDAsyncTask(taskDao).execute(id)
    }

    fun getAllTask(): LiveData<List<Task>> {
        return allTask
    }

    companion object {
        private class InsertTaskAsyncTask(val taskDao: TaskDao) : AsyncTask<Task, Unit, Unit>() {

            override fun doInBackground(vararg p0: Task?) {
                taskDao.insert(p0[0]!!)
            }
        }

        private class UpdateTaskAsyncTask(val taskDao: TaskDao) : AsyncTask<Task, Unit, Unit>() {

            override fun doInBackground(vararg p0: Task?) {
                taskDao.update(p0[0]!!)
            }
        }

        private class DeleteTaskAsyncTask(val taskDao: TaskDao) : AsyncTask<Task, Unit, Unit>() {

            override fun doInBackground(vararg p0: Task?) {
                taskDao.delete(p0[0]!!)
            }
        }

        private class DeleteIDAsyncTask(val taskDao: TaskDao) : AsyncTask<Int, Unit, Unit>() {
            override fun doInBackground(vararg p0: Int?) {
                taskDao.deleteTask(p0[0]!!)
            }

        }
    }
}