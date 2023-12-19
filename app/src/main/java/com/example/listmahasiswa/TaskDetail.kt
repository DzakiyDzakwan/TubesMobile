package com.example.listmahasiswa

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listmahasiswa.model.TaskClass
import java.text.SimpleDateFormat
import java.util.Locale

class TaskDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_list)

        val taskId = intent.getStringExtra("taskId")

        if (taskId != null) {
            val taskNameTextView: TextView = findViewById(R.id.textViewDetailTitle)
            taskNameTextView.text = "Task ID: $taskId"

        } else {
            val taskNameTextView: TextView = findViewById(R.id.textViewDetailTitle)
            taskNameTextView.text = "Mutia"
//            Toast.makeText(this, "Task ID not found", Toast.LENGTH_SHORT).show()
//            finish()
        }
    }
}
