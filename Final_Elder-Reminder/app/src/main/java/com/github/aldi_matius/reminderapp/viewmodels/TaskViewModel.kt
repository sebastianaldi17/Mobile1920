package com.github.aldi_matius.reminderapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.github.aldi_matius.reminderapp.data.TaskRepository
import com.github.aldi_matius.reminderapp.data.Task

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: TaskRepository =
        TaskRepository(application)
    private var allTask: LiveData<List<Task>> = repository.getAllTask()

    fun insert(task: Task) {
        repository.insert(task)
    }

    fun update(task: Task) {
        repository.update(task)
    }

    fun delete(task: Task) {
        repository.delete(task)
    }

    fun getAllTask(): LiveData<List<Task>> {
        return allTask
    }
}