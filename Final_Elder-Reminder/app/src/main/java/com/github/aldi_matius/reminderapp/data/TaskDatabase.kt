package com.github.aldi_matius.reminderapp.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao


    companion object {
        private var instance: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase? {
            if (instance == null) {
                synchronized(TaskDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java, "task_database"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
//                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

//        private val roomCallback = object : RoomDatabase.Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//                PopulateDbAsyncTask(instance).execute()
//            }
//        }
     }

//    class PopulateDbAsyncTask(db: TaskDatabase?) : AsyncTask<Unit, Unit, Unit>() {
//        private val taskDao = db?.taskDao()
//
//        override fun doInBackground(vararg p0: Unit?) {
//            taskDao?.insert(Task("title 1", "description 1", "13.00", "10 Oct 2019"))
//            taskDao?.insert(Task("title 2", "description 2", "17.00", "13 Oct 2019"))
//            taskDao?.insert(Task("title 3", "description 3", "18.00", "21 Oct 2019"))
//        }
//    }

}