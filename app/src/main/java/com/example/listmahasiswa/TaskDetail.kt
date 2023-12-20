package com.example.listmahasiswa

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listmahasiswa.api.APIService
import com.example.listmahasiswa.api.RetrofitClient
import com.example.listmahasiswa.model.ResponseCreateTaskModel
import com.example.listmahasiswa.model.TaskClass
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
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

        val buttonDelete: Button = findViewById(R.id.delete_task)
        buttonDelete.setOnClickListener {
            if (task != null && task.id != null) {
                deleteTask(task.id)
            } else {
                showToast("Task ID is null or blank.")
            }
        }
        // Check if the task is not null
        if (task != null)
        {
            val taskNameTextView: TextView = findViewById(R.id.edit_name)
            val taskDeadlineTextView: TextView = findViewById(R.id.edit_deadline)

            val taskName = "${task.name}"
            taskNameTextView.text = taskName

            val formattedDate = task.deadline_at?.let {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                dateFormat.format(it)
            } ?: "N/A"

            val deadlineText = "Deadline: ${formattedDate}"
            taskDeadlineTextView.text = deadlineText

            val btnUpdate: Button = findViewById(R.id.button_update)

            btnUpdate.setOnClickListener {
                val updatedName = taskNameTextView.text.toString()
                val updatedDeadlineText = taskDeadlineTextView.text.toString()

                var updatedDeadline: Date? = null

                try {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    updatedDeadline = dateFormat.parse(updatedDeadlineText)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.1.3:3333/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val apiService = retrofit.create(APIService::class.java)

                val updatedTask = task?.markAsFinished?.let { it1 ->
                    TaskClass(task?.id, updatedName,
                        it1, task?.started_at, updatedDeadline, task?.created_at, task?.updated_at)
                }

                val taskId = task?.id
                if (taskId != null) {
                    val call = apiService.updateTask(taskId, updatedTask)

                    call.enqueue(object : Callback<ResponseCreateTaskModel> {
                        override fun onResponse(call: Call<ResponseCreateTaskModel>, response: Response<ResponseCreateTaskModel>) {
                            if (response.isSuccessful) {
                                val updatedTask = response.body()
                                if (updatedTask != null) {
                                    taskNameTextView.text = "${updatedName}"
                                    taskDeadlineTextView.text = "${updatedDeadline}"
                                    val intent = Intent()
                                    setResult(Activity.RESULT_OK, intent)
                                    finish()
                                    Toast.makeText(this@TaskDetail, "Data updated successfully", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            } else {
                                Toast.makeText(this@TaskDetail, "Failed to update data", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<ResponseCreateTaskModel>, t: Throwable) {
                            Log.e("UpdateTask", "Failed to update data: ${t.message}")
                            Toast.makeText(this@TaskDetail, "Berhasil update data", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    })
                }
            }
        } else
        {
            Toast.makeText(this, "Task data not found", Toast.LENGTH_SHORT).show()
            finish()
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
