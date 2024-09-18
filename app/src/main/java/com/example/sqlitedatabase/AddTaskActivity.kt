package com.example.sqlitedatabase

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sqlitedatabase.database.DatabaseHelper
import com.example.sqlitedatabase.model.TaskListModel

class AddTaskActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var detailsEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button
    private var dbHandler: DatabaseHelper? = null
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        nameEditText = findViewById(R.id.nameEditText)
        detailsEditText = findViewById(R.id.detailsEditText)
        saveButton = findViewById(R.id.saveButton)
        deleteButton = findViewById(R.id.deleteButton)

        dbHandler = DatabaseHelper(this)
        val mode = intent.getStringExtra("Mode")

        if(intent != null && mode == "E"){

            isEditMode = true
            saveButton.text = "Update Data"
            deleteButton.visibility = View.VISIBLE
            val tasks : TaskListModel = dbHandler!!.getTask(intent.getIntExtra("ID",0))
            nameEditText.setText(tasks.name)
            detailsEditText.setText(tasks.details)

        }else{

            isEditMode = false
            saveButton.text = "Save Data"
            deleteButton.visibility = View.GONE

        }

        saveButton.setOnClickListener {
            var success : Boolean = false
            var tasks: TaskListModel = TaskListModel()
            if(isEditMode){
                //update data
                tasks.id = intent.getIntExtra("ID",0)
                tasks.name = nameEditText.text.toString()
                tasks.details = detailsEditText.text.toString()
                success = dbHandler?.updateTask(tasks) as Boolean

            }else{
                //insert data
                tasks.name = nameEditText.text.toString()
                tasks.details = detailsEditText.text.toString()
                success = dbHandler?.addTask(tasks) as Boolean

                val task : TaskListModel = dbHandler!!.getTask(intent.getIntExtra("ID",0))
                nameEditText.setText(task.name)
                detailsEditText.setText(task.details)

            }
            if(success){
                val i = Intent(applicationContext, MainActivity::class.java)
                startActivity(i)
                finish()
            }else{
                Toast.makeText(applicationContext, "Something Went Wrong!!", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click yes if you want to Delete the task")
                .setPositiveButton("Yes", { dialog, i ->
                    val success = dbHandler?.deleteTask(intent.getIntExtra("ID", 0)) as Boolean
                    if(success){
                        finish()
                        dialog.dismiss()
                    }
                })
                .setNegativeButton("No", { dialog, i ->
                    dialog.dismiss()
                })
            dialog.show()
        }

    }
}