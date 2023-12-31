package com.example.listmahasiswa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listmahasiswa.model.TaskClass
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class TaskAdapterClass(private val taskList: ArrayList<TaskClass>): RecyclerView.Adapter<TaskAdapterClass.ViewHolderTaskClass>() {

    var onItemClick: ((TaskClass)->Unit) ? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTaskClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolderTaskClass(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderTaskClass, position: Int)
    {
        val currentItem = taskList[position]
        holder.rvTaskName.text = currentItem.name
        val formatter = DateTimeFormatter.ofPattern("dd MMM YYYY", Locale.getDefault())
        val formattedDate =
            currentItem.deadline_at?.toInstant()?.atZone(ZoneId.systemDefault())?.format(formatter)
        holder.rvTaskDeadline.text = formattedDate

        holder.rvTaskStatus.isChecked = currentItem.markAsFinished

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size

    }

    class ViewHolderTaskClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvTaskName:TextView = itemView.findViewById(R.id.task_name)
        val rvTaskDeadline:TextView = itemView.findViewById(R.id.task_date)
        val rvTaskStatus:CheckBox = itemView.findViewById(R.id.task_status)
    }
}
