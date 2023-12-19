package com.example.listmahasiswa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView // Mengganti 'searchView' menjadi 'SearchView'
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import com.example.listmahasiswa.api.RetrofitClient
import com.example.listmahasiswa.model.ResponseModel
import com.example.listmahasiswa.model.TaskClass
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ToDoListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var taskList: ArrayList<TaskClass> = arrayListOf()
    private lateinit var taskAdapter: TaskAdapterClass

    var task: ArrayList<TaskClass> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        val apiService = RetrofitClient.apiService

        val call = apiService.getData()

        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
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
                } else {
                    // Handle error response
                    Log.e("API Response", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                // Handle network failure
                Log.e("API Response", "Failure: ${t.message}")
            }
        })

        // Set up SearchView
        val searchView = findViewById<SearchView>(R.id.searchView) // Mengganti 'searchView' menjadi 'SearchView'
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission if needed
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search query text change
                filterTaskList(newText)
                return true
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

        val buttonAdd = findViewById<FloatingActionButton>(R.id.buttonAddTodo)

        buttonAdd.setOnClickListener {
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun filterTaskList(query: String?) {
        taskList.clear()
        if (query.isNullOrBlank()) {
            taskList.addAll(task)
        } else {
            val filteredList = task.filter { it.name.contains(query, ignoreCase = true) }
            taskList.addAll(filteredList)
        }
        taskAdapter.notifyDataSetChanged()
    }
}
