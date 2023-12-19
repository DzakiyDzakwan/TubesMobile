package com.example.listmahasiswa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import com.example.listmahasiswa.api.RetrofitClient
import com.example.listmahasiswa.model.ResponseModel
import com.example.listmahasiswa.model.TaskClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ToDoListActivity : AppCompatActivity()
{

    private lateinit var recyclerView: RecyclerView
    private var taskList: ArrayList<TaskClass> = arrayListOf()
    private lateinit var taskAdapter: TaskAdapterClass

    var task: ArrayList<TaskClass> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        val apiService = RetrofitClient.apiService

        val call = apiService.getData()

        call.enqueue(object : Callback<ResponseModel>
                     {
                         override fun onResponse(call: Call<ResponseModel>,
                                                 response: Response<ResponseModel>)
                         {
                             if (response.isSuccessful)
                             {
                                 val taskResponse = response.body()
                                 val tasks = taskResponse?.data

                                 // Process the list of tasks
                                 tasks?.let {
                                     taskList.addAll(it)
                                     task.addAll(it)
                                     taskAdapter.notifyDataSetChanged()
                                     // Now tasksList contains all the tasks obtained from Retrofit
                                     taskList.forEach { task ->
                                         // Handle each task
                                         Log.d("Task", "ID: ${task.id}, Name: ${task.name}")
                                     }
                                 }
                             }
                             else
                             {
                                 // Handle error response
                                 Log.e("API Response", "Error: ${response.code()}")
                             }
                         }

                         override fun onFailure(call: Call<ResponseModel>, t: Throwable)
                         {
                             // Handle network failure
                             Log.e("API Response", "Failure: ${t.message}")
                         }
                     })

        recyclerView = findViewById(R.id.recyclerViewTodo)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        taskAdapter = TaskAdapterClass(taskList)
        recyclerView.adapter = taskAdapter
        taskAdapter.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
        }
    }
}
