package com.example.listmahasiswa

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import com.example.listmahasiswa.api.RetrofitClient
import com.example.listmahasiswa.model.CreateTaskModel
import com.example.listmahasiswa.model.ResponseCreateTaskModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class CreateTaskActivity : AppCompatActivity()
{
    lateinit var datePickerButton: ImageView
    lateinit var submitButton: Button
    lateinit var dateEditText: EditText
    lateinit var taskTitle: EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_task)

        datePickerButton = findViewById(R.id.imageView)
        dateEditText = findViewById(R.id.editTextDate)
        submitButton = findViewById(R.id.button2)
        taskTitle = findViewById(R.id.editTextText2)

        datePickerButton.setOnClickListener {
            showDatePicker()
        }

        submitButton.setOnClickListener {
            val title = taskTitle.text.toString()
            val selectedDateString = dateEditText.text.toString()
            if (title.isNullOrBlank() or selectedDateString.isEmpty() or selectedDateString.equals(""))
            {
                Toast.makeText(this@CreateTaskActivity,
                               "Tolong Isilah Jangan Malas Kali",
                               Toast.LENGTH_SHORT).show()
//                finish()
            }
            else
            {
                val selectedDate = parseDateString(selectedDateString)
                val formattedDate = formatDateToAPI(selectedDate)
                submitData(title, formattedDate)
                finish()
            }
        }
    }

    private fun parseDateString(dateString: String): Date
    {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.parse(dateString) ?: Date() // Handle parsing errors as needed
    }

    private fun formatDateToAPI(date: Date): String
    {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(date)
    }

    private fun showDatePicker()
    {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                // Do something with the selected date
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dateEditText.setText(selectedDate)
            },
            year,
            month,
            day
        )

        // Show the date picker dialog
        datePickerDialog.show()
    }

    private fun submitData(title: String, formattedDate: String)
    {
        val task = CreateTaskModel(title, formattedDate)
        val call = RetrofitClient.apiService.createTask(task)

        call.enqueue(object : Callback<ResponseCreateTaskModel>
                     {
                         override fun onResponse(call: Call<ResponseCreateTaskModel>,
                                                 response: Response<ResponseCreateTaskModel>)
                         {
                             // Handle success
                             if (response.isSuccessful)
                             {
                                 val responseBody = response.body()
                                 Log.d("Task", "ID: ${responseBody}, Name: ${task.deadline_at}")
                             }
                             else
                             {
                                 // Handle error
                             }
                         }

                         override fun onFailure(call: Call<ResponseCreateTaskModel>, t: Throwable)
                         {
                             Log.e("API Response", "Failure: ${t.message}")
                         }
                     })
    }
}