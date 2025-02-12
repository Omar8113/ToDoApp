package com.route.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.route.todoapp.database.dao.TasksDao
import com.route.todoapp.database.entity.Task

@Database(entities = [Task::class], version = 1, exportSchema = true)
abstract class MyDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object {
        private const val DATABASE_NAME = "Tasks"
        private var myDatabase: MyDatabase? = null

        fun init(applicationContext: Context) {
            if (myDatabase == null) {
                myDatabase = Room.databaseBuilder(
                    applicationContext,
                    MyDatabase::class.java, DATABASE_NAME
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build()
            }
        }

        fun getInstance(): MyDatabase? {
            return myDatabase
        }
    }
}