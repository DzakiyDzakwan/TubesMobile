package com.example.listmahasiswa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

       mahasiswa = arrayListOf(
            MahasiswaClass("211402006", R.drawable.donny, "Donny Adithya", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402009", R.drawable.avatar2, "Mutia Rahmah", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402018", R.drawable.avatar3, "Talitha Syafiyah", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402036", R.drawable.avatar4, "Fatma Ananta Sari", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402075", R.drawable.dzakiy, "Dzakiy Dzakwan", "dzakcart@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5),
            MahasiswaClass("211402084", R.drawable.caesto, "Caesto Marco", "example@gmail.com", "Ilmu Komputer dan Teknologi Informasi", "Teknologi Informasi", 5)
        )

        imageList = arrayOf(
            R.drawable.donny,
            R.drawable.avatar2,
            R.drawable.avatar3,
            R.drawable.avatar4,
            R.drawable.dzakiy,
            R.drawable.caesto
        )

        nameList = arrayOf(
            "Donny Adithya",
            "Mutia Rahmah",
            "Talitha Syafiyah",
            "Fatma Ananta Sari",
            "Dzakiy Dzakwan",
            "Caesto Marco"
        )

        recyclerView = findViewById(R.id.recyleView)
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