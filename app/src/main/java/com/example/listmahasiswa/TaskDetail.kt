package com.example.listmahasiswa

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listmahasiswa.model.TaskClass
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Locale

class TaskDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_list)

        // Retrieve the task data from the intent
        val json = intent.getStringExtra("taskId")

        val gson = Gson()
        val task = gson.fromJson(json, TaskClass::class.java)

        // Check if the task is not null
        if (task != null)
        {
            // Now you can use the task data to populate your views
            val taskNameTextView: TextView = findViewById(R.id.textViewDetailTitle)
            val taskDeadlineTextView: TextView = findViewById(R.id.textViewDetailDate)

            val taskName = "Task Name: ${task.name}"
            taskNameTextView.text = taskName

            // Check if the deadline_at property is not null before formatting
            val formattedDate = task.deadline_at?.let {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                dateFormat.format(it)
            } ?: "N/A" // Provide a default value if deadline_at is null

            val deadlineText = "Deadline: ${formattedDate}"
            taskDeadlineTextView.text = deadlineText
        } else
        {
            // Handle the case where task is null
            Toast.makeText(this, "Task data not found", Toast.LENGTH_SHORT).show()
            finish() // Close the activity if task data is not found
        }
    }
}
