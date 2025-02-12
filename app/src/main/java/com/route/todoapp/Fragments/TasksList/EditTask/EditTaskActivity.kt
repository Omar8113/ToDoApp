package com.route.todoapp.Fragments.TasksList.EditTask

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.route.todoapp.database.MyDatabase
import com.route.todoapp.database.entity.Task
import com.route.todoapp.databinding.ActivityEditTaskBinding
import java.util.Calendar

class EditTaskActivity : AppCompatActivity() {
    val date = Calendar.getInstance()
    lateinit var binding: ActivityEditTaskBinding
    lateinit var editTask: Task
    lateinit var editedTask: Task

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        editTask = intent.getParcelableExtra("task", Task::class.java) as Task
        binding.dateTv.text = "${date.get(android.icu.util.Calendar.DAY_OF_MONTH)} - ${
            date.get(
                android.icu.util.Calendar.MONTH
            ) + 1
        } - ${
            date.get(
                android.icu.util.Calendar.YEAR
            )
        }"
        editedTask = editTask.copy()
        initInfo()
        saveChanges()
    }

    private fun saveChanges() {
        binding.saveBtn.setOnClickListener {
            editedTask.title = binding.TitleTil.editText?.text.toString()
            editedTask.description = binding.descriptionTil.editText?.text.toString()
            editedTask.date = date.timeInMillis
            MyDatabase.getInstance()?.tasksDao()?.updateTask(editedTask)
            finish()
        }
    }

    private fun initInfo() {
        binding.TitleTil.editText?.setText(editTask.title)
        binding.descriptionTil.editText?.setText(editTask.description)
        setDate()

    }

    private fun setDate() {
        binding.dateTv.setOnClickListener {
            val datePicker = DatePickerDialog(
                this,
                { it, year, month, day ->
                    date.set(android.icu.util.Calendar.DAY_OF_MONTH, day)
                    date.set(android.icu.util.Calendar.MONTH, month)
                    date.set(android.icu.util.Calendar.YEAR, year)
                    binding.dateTv.text = "${date.get(android.icu.util.Calendar.DAY_OF_MONTH)} - ${
                        date.get(
                            android.icu.util.Calendar.MONTH
                        ) + 1
                    } - ${
                        date.get(
                            android.icu.util.Calendar.YEAR
                        )
                    }"
                },
                date.get(android.icu.util.Calendar.YEAR),
                date.get(android.icu.util.Calendar.MONTH),
                date.get(
                    android.icu.util.Calendar.DAY_OF_MONTH
                )
            )
            datePicker.datePicker.minDate = System.currentTimeMillis()
            datePicker.show()
        }
    }

}