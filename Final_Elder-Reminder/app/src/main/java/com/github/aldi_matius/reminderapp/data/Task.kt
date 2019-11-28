package com.github.aldi_matius.reminderapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(

    @ColumnInfo(name = "Title")
    var title: String,

    @ColumnInfo(name = "Description")
    var description: String,

    @ColumnInfo(name = "Time")
    var duetime: String,

    @ColumnInfo(name = "Date")
    var duedate: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}