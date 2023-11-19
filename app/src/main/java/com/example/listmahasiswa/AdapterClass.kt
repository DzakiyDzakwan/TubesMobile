package com.example.listmahasiswa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterClass(private val mahasiswaList: ArrayList<MahasiswaClass>): RecyclerView.Adapter<AdapterClass.ViewHolderClass>() {

    var onItemClick: ((MahasiswaClass)->Unit) ? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = mahasiswaList[position]
        holder.rvStudentImage.setImageResource(currentItem.image)
        holder.rvStudentName.text = currentItem.name

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return mahasiswaList.size

    }

    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvStudentImage:ImageView = itemView.findViewById(R.id.student_image)
        val rvStudentName:TextView = itemView.findViewById(R.id.student_name)
    }
}
