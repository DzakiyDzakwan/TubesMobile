package com.example.listmahasiswa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.listmahasiswa.api.RetrofitClient
import com.example.listmahasiswa.model.CreateTaskModel
import com.example.listmahasiswa.model.ResponseCreateTaskModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TempActivity : AppCompatActivity() {

    lateinit var submitButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)

        submitButton = findViewById(R.id.submit_button)

    }

    private fun submitData(formattedDate: String)
    {
        val task = CreateTaskModel("Task Title", formattedDate)
        val call = RetrofitClient.apiService.createTask(task)

        call.enqueue(object : Callback<ResponseCreateTaskModel>
        {
            override fun onResponse(call: Call<ResponseCreateTaskModel>,
                                    response: Response<ResponseCreateTaskModel>
            )
            {
                // Handle success
                if (response.isSuccessful)
                {
                    val responseBody = response.body()
                    Log.d("Task", "ID: ${task.name}, Name: ${task.deadline_at}")
                }
                else
                {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<ResponseCreateTaskModel>, t: Throwable)
            {
                Log.e("API Response", "Failure: ${t.message}")
            }
        })
    }
}