package com.example.sqlitedatabase.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedatabase.AddTaskActivity
import com.example.sqlitedatabase.R
import com.example.sqlitedatabase.model.TaskListModel

class TaskListAdapter(private var taskList: List<TaskListModel>, applicationContext: Context) : RecyclerView.Adapter<TaskViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_task_list,parent,false)
        return TaskViewHolder(view)
    }

    override fun getItemCount() = taskList.size


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.name.text = task.name
        holder.details.text = task.details


        holder.edit.setOnClickListener {
            val i = Intent(holder.itemView.context, AddTaskActivity::class.java)
            i.putExtra("ID",task.id)
            i.putExtra("Mode","E")
            holder.itemView.context.startActivity(i)
        }
    }
}