package com.route.todoapp.Fragments

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.route.todoapp.R
import com.route.todoapp.database.MyDatabase
import com.route.todoapp.database.entity.Task
import com.route.todoapp.databinding.FragmentAddTaskBinding

class AddTaskFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentAddTaskBinding
    var date = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateTaskInput()
        binding.dateTv.text =
            "${date.get(Calendar.DAY_OF_MONTH)} - ${date.get(Calendar.MONTH) + 1} - ${
                date.get(
                    Calendar.YEAR
                )
            }"
        setDate()
    }


    private fun addTask() {
        binding.addBtn.setOnClickListener {
            val task = Task(
                title = binding.TitleTil.editText?.text.toString(),
                description = binding.descriptionTil.editText?.text.toString(),
                date = date.timeInMillis,
                isDone = false
            )
            MyDatabase.getInstance()?.tasksDao()?.insertNewTask(task)
            dismiss()
        }
    }

    private fun setDate() {
        binding.dateTv.setOnClickListener {
            val datePicker = DatePickerDialog(requireContext(), { it, year, month, day ->
                date.set(Calendar.DAY_OF_MONTH, day)
                date.set(Calendar.MONTH, month)
                date.set(Calendar.YEAR, year)
                binding.dateTv.text =
                    "${date.get(Calendar.DAY_OF_MONTH)} - ${date.get(Calendar.MONTH) + 1} - ${
                        date.get(
                            Calendar.YEAR
                        )
                    }"
            }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
            datePicker.datePicker.minDate = System.currentTimeMillis()
            datePicker.show()
        }
    }

    private fun validateTaskInput() {
        binding.addBtn.setOnClickListener {
            var isValid = true
            if (binding.TitleTil.editText?.text?.trim().isNullOrEmpty()) {
                binding.TitleTil.error = getString(R.string.please_enter_task_title)
                isValid = false
                return@setOnClickListener
            } else {
                binding.TitleTil.error = null
            }
            if (binding.descriptionTil.editText?.text?.trim().isNullOrEmpty()) {
                binding.descriptionTil.error = getString(R.string.please_enter_task_description)
                isValid = false
                return@setOnClickListener
            } else {
                binding.descriptionTil.error = null
            }
            if (isValid == true) {
                addTask()
            } else {
                return@setOnClickListener
            }
        }
    }

    fun CalendarDay.toMillSeconds(): Long {
        val calendar = Calendar.getInstance()
        calendar.updateDate(this.year, this.month, this.day)
        return calendar.timeInMillis
    }

    fun Calendar.updateDate(year: Int, month: Int, day: Int) {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, day)
    }


}