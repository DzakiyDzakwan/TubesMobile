package com.example.listmahasiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.inappmessaging.model.Button

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private  lateinit var mahasiswaList: ArrayList<MahasiswaClass>

    lateinit var mahasiswa: ArrayList<MahasiswaClass>
    lateinit var imageList: Array<Int>
    lateinit var nameList: Array<String>

    private lateinit var  myAdapter: AdapterClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonToDoList: android.widget.Button = findViewById(R.id.buttonToDoList)
        buttonToDoList.setOnClickListener {
            val intent = Intent(this, ToDoListActivity::class.java)
            startActivity(intent)
        }

       mahasiswa = arrayListOf(
            MahasiswaClass("211402006", R.drawable.donny, "Donny Adithya", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402009", R.drawable.mutia, "Mutia Rahmah", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402018", R.drawable.talitha, "Talitha Syafiyah", "talithasyafiyah1112@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402024", R.drawable.jauza, "Jauza Hayah Anbari", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402036", R.drawable.fatma, "Fatma Ananta Sari", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402045", R.drawable.anhar, "Al Anhar Sufi", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402075", R.drawable.dzakiy, "Dzakiy Dzakwan", "dzakcart@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402084", R.drawable.caesto, "Caesto Marco", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402090", R.drawable.neha, "Neha Sabila Nazmira", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402111", R.drawable.vincent, "Vincent Enrique", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5)
        )

        imageList = arrayOf(
            R.drawable.donny,
            R.drawable.mutia,
            R.drawable.talitha,
            R.drawable.jauza,
            R.drawable.fatma,
            R.drawable.anhar,
            R.drawable.dzakiy,
            R.drawable.caesto,
            R.drawable.neha,
            R.drawable.vincent
        )

        nameList = arrayOf(
            "Donny Adithya",
            "Mutia Rahmah",
            "Talitha Syafiyah",
            "Jauza Hayah Anbari",
            "Fatma Ananta Sari",
            "Al Anhar Sufi",
            "Dzakiy Dzakwan",
            "Caesto Marco",
            "Neha Sabila Nazmira",
            "Vincent EnriqueL"
        )

        recyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        mahasiswaList = arrayListOf<MahasiswaClass>()
//        getData()

        myAdapter = AdapterClass(mahasiswa)
        recyclerView.adapter = myAdapter
        myAdapter.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
        }
    }

    private fun getData() {



//        for (i in imageList.indices){
//            val dataClass = MahasiswaClass(imageList[i], nameList[i])
//            mahasiswaList.add(dataClass)
//        }

//        recyclerView.adapter = AdapterClass(mahasiswaList)
    }
}