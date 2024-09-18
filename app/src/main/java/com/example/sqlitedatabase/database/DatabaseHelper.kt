package com.example.sqlitedatabase.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.activity.result.contract.ActivityResultContracts
import com.example.sqlitedatabase.model.TaskListModel

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {


    companion object{

        private val DB_NAME = "task"
        private val DB_VERSION = 1
        private val TABLE_NAME = "tasklist"
        private val ID = "id"
        private val TASK_NAME = "taskname"
        private val TASK_DETAILS = "taskdetails"


    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $TASK_NAME TEXT, $TASK_DETAILS TEXT)"
        p0?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }


    // Read Operation
    @SuppressLint("Range")
    fun getAllTask(): List<TaskListModel> {

        val taskList = ArrayList<TaskListModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val task = TaskListModel()
                    task.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    task.name = cursor.getString(cursor.getColumnIndex(TASK_NAME))
                    task.details = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
                    taskList.add(task)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return taskList
    }

    // Insert Operation
    fun addTask(task: TaskListModel): Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TASK_NAME, task.name)
        values.put(TASK_DETAILS,task.details)
        val success = db.insert(TABLE_NAME,null,values)
        db.close()
        return ( Integer.parseInt("$success") != -1)
    }

    // Select the data of particular id
    @SuppressLint("Range")
    fun getTask(_id: Int): TaskListModel{
        val task = TaskListModel()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery,null)

        cursor?.moveToFirst()
        task.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        task.name = cursor.getString(cursor.getColumnIndex(TASK_NAME))
        task.details = cursor.getString(cursor.getColumnIndex(TASK_DETAILS))
        cursor?.close()
        return task
    }

    //Delete a particular task
    fun deleteTask(_id: Int):Boolean{
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return ( Integer.parseInt("$_success") != -1 )
    }

    //Update Task
    fun updateTask(task: TaskListModel):Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TASK_NAME,task.name)
        values.put(TASK_DETAILS,task.details)
        val _success = db.update(TABLE_NAME,values, ID + "=?", arrayOf(task.id.toString())).toLong()
        db.close()
        return ( Integer.parseInt("$_success") != -1 )
    }

}