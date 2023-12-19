package com.example.listmahasiswa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        taskAdapter.onItemClick = { taskItem ->
            val intent = Intent(this, TaskDetail::class.java)
            intent.putExtra("taskId", taskId)
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
