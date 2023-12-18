package com.example.listmahasiswa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapterClass(private val taskList: ArrayList<TaskClass>): RecyclerView.Adapter<TaskAdapterClass.ViewHolderTaskClass>() {

    var onItemClick: ((TaskClass)->Unit) ? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTaskClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolderTaskClass(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderTaskClass, position: Int) {
        val currentItem = taskList[position]
        holder.rvTaskName.text = currentItem.name

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size

    }

    class ViewHolderTaskClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvTaskName:TextView = itemView.findViewById(R.id.student_name)
    }
}
