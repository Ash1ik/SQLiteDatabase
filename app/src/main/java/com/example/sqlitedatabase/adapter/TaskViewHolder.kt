package com.example.sqlitedatabase.adapter

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedatabase.R

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val name : TextView = itemView.findViewById(R.id.txt_name)
    val details : TextView = itemView.findViewById(R.id.txt_details)
    val edit : Button = itemView.findViewById(R.id.button_edit)

}