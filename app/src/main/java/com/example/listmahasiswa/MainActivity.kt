package com.example.listmahasiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmahasiswa.api.RetrofitClient
import com.example.listmahasiswa.model.ResponseModel
import com.example.listmahasiswa.model.TaskClass
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.random.Random
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity()
{

    private lateinit var recyclerView: RecyclerView
    private var task: ArrayList<TaskClass> = arrayListOf()
    private var taskList: ArrayList<TaskClass> = arrayListOf()
    private lateinit var taskAdapter: TaskAdapterClass

    private val gson: Gson = GsonBuilder()
        .setFieldNamingStrategy(CustomNamingStrategy)
        .create()

    // ...

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        task = arrayListOf(
//            TaskClass(null, "Tugas 1", true, null, getRandomDate(), Date(), Date()),
//            TaskClass(null, "Tugas 2", false, null, getRandomDate(), Date(), Date()),
//            TaskClass(null, "Tugas 3", false, null, getRandomDate(), Date(), Date()),
//            TaskClass(null, "Tugas 4", false, null, getRandomDate(), Date(), Date()),
//            TaskClass(null, "Tugas 5", false, null, getRandomDate(), Date(), Date()),
//            TaskClass(null, "Tugas 6", false, null, getRandomDate(), Date(), Date()),
//        )

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

                    tasks?.let {
                        taskList.addAll(it)
                        task.addAll(it)
                        taskAdapter.notifyDataSetChanged()
                        taskList.forEach { task ->
                            Log.d("Task", "ID: ${task.id}, Name: ${task.name}")
                        }
                    }
                } else {
                    Log.e("API Response", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.e("API Response", "Failure: ${t.message}")
            }
        })

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterTaskList(newText)
                return true
            }
        })

        recyclerView = findViewById(R.id.recyclerViewTodo)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        taskAdapter = TaskAdapterClass(taskList)
        recyclerView.adapter = taskAdapter
        taskAdapter.onItemClick = { task ->
            task?.id?.let {
                val intent = Intent(this, TaskDetail::class.java)
                val json = gson.toJson(task)
                Log.d("Tets", json)
                intent.putExtra("taskId", json)
                startActivity(intent)
            }
        }


        val buttonAdd = findViewById<FloatingActionButton>(R.id.buttonAddTodo)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }

//        val buttonDelete = findViewById<ImageView>(R.id.delete_task)
//        buttonDelete.setOnClickListener {
//            val taskId = task.firstOrNull()?.id
//            if (taskId != null) {
//                deleteTask(taskId)
//            } else {
//                showToast("Task ID is null or blank.")
//            }
//        }

    }

    private fun getRandomDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, Random.nextInt(1, 30)) // Random day between 1 and 30
        return calendar.time
    }

    private fun filterTaskList(query: String?) {
        taskList.clear()
        if (query.isNullOrBlank()) {
            taskList.addAll(task)
            Log.d("FilterTaskList", "Original taskList: $taskList")
        } else
        {
            taskList.addAll(task)
            val filteredList = taskList.filter { it.name.contains(query, ignoreCase = true) }
            Log.d("FilterTaskList", "Filtered list: $filteredList")
            taskList.clear()
            taskList.addAll(filteredList)
        }
        taskAdapter.notifyDataSetChanged()
    }

//    private fun deleteTask(taskId: Int?) {
//        val apiService = RetrofitClient.apiService
//        val call = apiService.deleteTask(taskId)
//
//        call.enqueue(object : Callback<Void> {
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                if (response.isSuccessful) {
//                    showToast("Task successfully deleted")
//                    finish()
//                } else {
//                    showToast("Failed to delete task")
//                }
//            }
//
//            override fun onFailure(call: Call<Void>, t: Throwable) {
//                showToast("Failed to connect to the server")
//            }
//        })
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//    }
}
