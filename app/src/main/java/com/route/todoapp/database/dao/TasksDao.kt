package com.route.todoapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.route.todoapp.database.entity.Task

@Dao
interface TasksDao {

    @Insert
    fun insertNewTask(task: Task)

    @Query("select * from Task")
    fun getAllTasks(): LiveData<List<Task>>

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("select * from Task where date = :date")
    fun getAllTasksByDate(date: Long): List<Task>

    @Query("select * from Task where id = :id")
    fun getTaskById(id: Int): Task?

    @Query("select * from Task where isDone = 0 ")
    fun getUnCompletedTasks(): List<Task>
}