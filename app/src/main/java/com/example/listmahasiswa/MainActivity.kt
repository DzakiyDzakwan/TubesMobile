package com.example.listmahasiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
//import android.widget.Button
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

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    //private  lateinit var mahasiswaList: ArrayList<MahasiswaClass>

    private lateinit var mahasiswa: ArrayList<MahasiswaClass>
    private lateinit var tasks: ArrayList<TaskClass>
    private var taskList: ArrayList<TaskClass> = arrayListOf()

    //private lateinit var  myAdapter: AdapterClass
    private  lateinit var taskAdapter: TaskAdapterClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val buttonToDoList: android.widget.Button = findViewById(R.id.buttonToDoList)
//        buttonToDoList.setOnClickListener {
//            val intent = Intent(this, ToDoListActivity::class.java)
//            startActivity(intent)
//        }

        tasks = arrayListOf(
            TaskClass(null, "Tugas 1", true, null, getRandomDate(), Date(), Date()),
            TaskClass(null, "Tugas 2", false, null, getRandomDate(), Date(), Date()),
            TaskClass(null, "Tugas 3", false, null, getRandomDate(), Date(), Date()),
            TaskClass(null, "Tugas 4", false, null, getRandomDate(), Date(), Date()),
            TaskClass(null, "Tugas 5", false, null, getRandomDate(), Date(), Date()),
            TaskClass(null, "Tugas 6", false, null, getRandomDate(), Date(), Date()),
            // Add more tasks as needed
        )

        val apiService = RetrofitClient.apiService

        val call = apiService.getData()

        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    val taskResponse = response.body()
                    val tasks = taskResponse?.data

                    tasks?.let {
                        taskList.addAll(it)
//                        tasks.addAll(it)

                        if(taskList.isEmpty()) {
                            taskList.addAll(tasks)
                        }

                        taskList.forEach { task ->
                            Log.d("Task", "ID: ${task.id}, Name: ${task.name}")
                        }
                        taskAdapter.notifyDataSetChanged()
                    }
                } else {
                    Log.e("API Response", "Error: ${response.code()}")
                    taskList.addAll(tasks)
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

        // List Mahasiswa
//        myAdapter = AdapterClass(mahasiswa)
//        recyclerView.adapter = myAdapter
//        myAdapter.onItemClick = {
//            val intent = Intent(this, DetailActivity::class.java)
//            intent.putExtra("android", it)
//            startActivity(intent)
//        }

        taskAdapter = TaskAdapterClass(taskList)

        recyclerView.adapter = taskAdapter
        taskAdapter.onItemClick = {
            val intent = Intent(this, TaskDetail::class.java)
            intent.putExtra("taskId", it)
            startActivity(intent)

        }

        val buttonAdd = findViewById<FloatingActionButton>(R.id.buttonAddTodo)

        buttonAdd.setOnClickListener {
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }
    }

   private fun getRandomDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, Random.nextInt(1, 30)) // Random day between 1 and 30
        return calendar.time
    }

    private fun filterTaskList(query: String?) {
        taskList.clear()
        if (query.isNullOrBlank()) {
            taskList.addAll(tasks)
        } else {
            val filteredList = tasks.filter { it.name.contains(query, ignoreCase = true) }
            taskList.addAll(filteredList)
        }
        taskAdapter.notifyDataSetChanged()
    }
}