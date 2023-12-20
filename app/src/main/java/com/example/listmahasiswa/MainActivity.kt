package com.example.listmahasiswa

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
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
    companion object {
        const val REQUEST_CODE_EDIT = 1 // Ganti angka 1 dengan nilai yang sesuai
    }

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
        taskAdapter.onItemClick = {
            val intent = Intent(this, TaskDetail::class.java)
            val json = gson.toJson(it)
            Log.d("Tets", json)
            intent.putExtra("taskId", json)
            startActivityForResult(intent, REQUEST_CODE_EDIT)
        }

        val buttonAdd = findViewById<FloatingActionButton>(R.id.buttonAddTodo)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume()
    {
        super.onResume()
        // Refresh data when returning to this page
        loadData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDIT && resultCode == Activity.RESULT_OK)
        {
            // Panggil metode loadData() atau perbarui data sesuai dengan kebutuhan Anda
            loadData()
        }
    }

    private fun loadData()
    {
        // Di sini, Anda perlu mengambil data terbaru melalui Retrofit atau metode lainnya
        // Misalnya, jika Anda memiliki fungsi Retrofit untuk mengambil data, Anda bisa melakukan sesuatu seperti ini:
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
                                 val newData = taskResponse?.data

                    newData?.let {
                        taskList.clear()
                        taskList.addAll(it) // newData adalah data yang diperbarui
                        taskAdapter.notifyDataSetChanged()
                    }
                } else {
                    Log.e("API Response", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Log.e("API Response", "Failure: ${t.message}")
            }
        })
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
}
