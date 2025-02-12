package com.route.todoapp.Fragments.TasksList

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.route.todoapp.R
import com.route.todoapp.database.entity.Task
import com.route.todoapp.databinding.ItemTaskBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TasksAdapter(val tasksList: MutableList<Task>?, val onTaskClick: OnTaskClickListener) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun markTaskDone(task: Task) {
            if (task.isDone == true) {
                binding.verticalLine.setBackgroundColor(Color.GREEN)
                binding.taskTitleTv.setTextColor(Color.GREEN)
                binding.doneBtn.setImageResource(R.drawable.done)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = tasksList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasksList?.get(position)
        holder.binding.taskTitleTv.text = task?.title
        holder.binding.taskTimeTv.text =
            (task?.date?.let { formatDate(it) } ?: "No Date").toString()
        if (task != null) {
            holder.markTaskDone(task)
        }

        holder.binding.delete.setOnClickListener {
            if (task != null) {
                onTaskClick.onDeleteClickListener(task)
            }
        }
        holder.binding.doneBtn.setOnClickListener {
            if (task != null) {
                onTaskClick.onDoneClickListener(task)
            }
        }
        holder.binding.task.setOnClickListener {
            if (task != null) {
                onTaskClick.onItemClickListener(task)
            }
        }

    }


    private fun formatDate(it: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
        return sdf.format(Date(it))
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTasks(newTasks: List<Task>) {
        tasksList?.clear()
        tasksList?.addAll(newTasks)
        notifyDataSetChanged()
    }


    interface OnTaskClickListener {
        fun onItemClickListener(task: Task)
        fun onDeleteClickListener(task: Task)
        fun onDoneClickListener(task: Task)
    }
}