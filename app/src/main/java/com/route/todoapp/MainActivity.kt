package com.route.todoapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.route.todoapp.Fragments.AddTaskFragment
import com.route.todoapp.Fragments.Settings.SettingsFragment
import com.route.todoapp.Fragments.TasksList.TasksListFragment
import com.route.todoapp.database.MyDatabase
import com.route.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MyDatabase.getInstance()
        showFragment(TasksListFragment())
        initFragment()
        binding.addBtn.setOnClickListener {
            AddTaskFragment().show(supportFragmentManager, null)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initFragment() {
        binding.bottomNavView.setOnItemSelectedListener {
            if (it.itemId == R.id.ic_list) {
                binding.appBar.titleTv.text = "ToDo List"
                showFragment(TasksListFragment())
            } else {
                binding.appBar.titleTv.text = "Settings"
                showFragment(SettingsFragment())
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}