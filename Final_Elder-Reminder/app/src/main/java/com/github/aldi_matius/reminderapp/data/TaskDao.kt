package com.github.aldi_matius.reminderapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM task_table")
    fun getAllTask(): LiveData<List<Task>>

    @Query("DELETE FROM task_table WHERE id=:id")
    fun deleteTask(id :Int)
}