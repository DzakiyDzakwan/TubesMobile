package com.example.listmahasiswa

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import com.example.listmahasiswa.model.TaskClass

class ToDoListActivity : AppCompatActivity()
{

    private lateinit var recyclerView: RecyclerView
    private lateinit var taskList: ArrayList<TaskClass>

    lateinit var task: ArrayList<TaskClass>
    lateinit var nameList: Array<String>

    private lateinit var taskAdapter: TaskAdapterClass

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        task = arrayListOf(
            TaskClass("1", "Task 1", "example@gmail.com", "Ilmu Komputer"),
            TaskClass("2", "Tugas Kuliah", "example@gmail.com", "Ilmu Komputer"),
            TaskClass("3", "Pekerjaan Rumah", "example@gmail.com", "Teknologi Informasi"),
        )

        nameList = arrayOf(
            "Task 1",
            "Tugas Kuliah",
            "Pekerjaan Rumah"
        )

        recyclerView = findViewById(R.id.recyclerViewTodo)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        taskList = arrayListOf<TaskClass>()
//        getData()

        taskAdapter = TaskAdapterClass(task)
        recyclerView.adapter = taskAdapter
        taskAdapter.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
        }
    }
//
//    private fun getData() {
//        // Your implementation here
//    }
}
