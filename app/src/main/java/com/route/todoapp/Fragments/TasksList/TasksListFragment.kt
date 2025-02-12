package com.route.todoapp.Fragments.TasksList

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.route.todoapp.Fragments.TasksList.EditTask.EditTaskActivity
import com.route.todoapp.database.MyDatabase
import com.route.todoapp.database.entity.Task
import com.route.todoapp.databinding.FragmentTasksListBinding
import java.util.Calendar

class TasksListFragment : Fragment() {

    lateinit var binding: FragmentTasksListBinding
    lateinit var adapter: TasksAdapter
    val calendar = Calendar.getInstance()
    var selectedDay = com.prolificinteractive.materialcalendarview.CalendarDay.today()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initCalender()
    }

    private fun initCalender() {
        binding.calendarView.selectedDate = selectedDay
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            selectedDay = date
            filterTasks()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initRecyclerView() {
        adapter = TasksAdapter(mutableListOf(), object : TasksAdapter.OnTaskClickListener {
            override fun onItemClickListener(task: Task) {
                val intent = Intent(requireContext(), EditTaskActivity::class.java)
                intent.putExtra("task", task)
                startActivity(intent)
            }

            override fun onDeleteClickListener(task: Task) {
                MyDatabase.getInstance()?.tasksDao()?.deleteTask(task)
            }

            override fun onDoneClickListener(task: Task) {
                task.isDone = !task.isDone!!
                MyDatabase.getInstance()?.tasksDao()?.updateTask(task)
            }

        })
        binding.tasksRv.adapter = adapter
        filterTasks()

    }


    private fun filterTasks() {
        MyDatabase.getInstance()?.tasksDao()?.getAllTasks()?.observe(viewLifecycleOwner) { tasks ->
            var tasksList = tasks.filter {
                var taskDate = Calendar.getInstance()
                taskDate.timeInMillis = it.date!!
                return@filter taskDate.get(Calendar.YEAR) == selectedDay.year &&
                        taskDate.get(Calendar.MONTH) == selectedDay.month &&
                        taskDate.get(Calendar.DAY_OF_MONTH) == selectedDay.day
            }
            adapter.updateTasks(tasksList)

        }
    }


}