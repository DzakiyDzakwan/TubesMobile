package com.example.listmahasiswa

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listmahasiswa.api.RetrofitClient
import com.example.listmahasiswa.model.TaskClass
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class TaskDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_list)

        val json = intent.getStringExtra("taskId")
        val gson = Gson()
        val task = gson.fromJson(json, TaskClass::class.java)

        val btnDeleteTask: Button = findViewById(R.id.buttonDelete)

        if (task != null) {
            val taskNameTextView: TextView = findViewById(R.id.textViewDetailTitle)
            val taskDeadlineTextView: TextView = findViewById(R.id.textViewDetailDate)

            val taskName = "Task Name: ${task.name}"
            taskNameTextView.text = taskName

            val formattedDate = task.deadline_at?.let {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                dateFormat.format(it)
            } ?: "N/A"

            val deadlineText = "Deadline: ${formattedDate}"
            taskDeadlineTextView.text = deadlineText
        } else {
            Toast.makeText(this, "Task data not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnDeleteTask.setOnClickListener {
            if (task != null && task.id != null) {
                deleteTask(task.id)
            } else {
                showToast("Task ID is null or blank.")
            }
        }

    }

    private fun deleteTask(taskId: Int?) {
        val apiService = RetrofitClient.apiService
        val call = apiService.deleteTask(taskId)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Task successfully deleted")
                    finish()
                } else {
                    showToast("Failed to delete task")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showToast("Failed to connect to the server")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
