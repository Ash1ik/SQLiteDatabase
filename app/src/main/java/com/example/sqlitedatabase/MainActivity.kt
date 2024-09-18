package com.example.sqlitedatabase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitedatabase.adapter.TaskListAdapter
import com.example.sqlitedatabase.database.DatabaseHelper
import com.example.sqlitedatabase.model.TaskListModel

class MainActivity : AppCompatActivity() {

    private lateinit var recycler_task : RecyclerView
    private lateinit var btn_add : Button
    private var taskListAdapter: TaskListAdapter? = null
    private var dbHandler: DatabaseHelper? = null
    private var taskList: List<TaskListModel> = ArrayList<TaskListModel>()
    private var layoutManager : LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_task = findViewById(R.id.rv_list)
        btn_add = findViewById(R.id.ft_add_items)

        dbHandler = DatabaseHelper(this)
        fetchList()

        btn_add.setOnClickListener {
            val i = Intent(applicationContext, AddTaskActivity::class.java)
            startActivity(i)
        }


    }

    private fun fetchList(){
        taskList = dbHandler!!.getAllTask()
        taskListAdapter = TaskListAdapter(taskList,applicationContext)
        layoutManager = LinearLayoutManager(applicationContext)
        recycler_task.layoutManager = layoutManager
        recycler_task.adapter = taskListAdapter
        taskListAdapter?.notifyDataSetChanged()
    }

}