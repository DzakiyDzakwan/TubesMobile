package com.example.listmahasiswa

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val getData = intent.getParcelableExtra<MahasiswaClass>("android")
        if(getData != null) {
            val detailImage: ImageView = findViewById(R.id.detail_image)
            val detailTitle: TextView = findViewById(R.id.detail_name)
            val detailStudentId: TextView = findViewById(R.id.detail_student_id)
//            val studentName: TextView = findViewById(R.id.detail_s)

            detailImage.setImageResource(getData.image)
            detailTitle.text = getData.name
            detailStudentId.text = getData.id

            val detailTable: TableLayout = findViewById(R.id.detail_table)

            // Create a function to add a row with a label and value
            fun addRow(label: String, value: String) {
                val row = TableRow(this)

                val labelTextView = TextView(this)
                labelTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1.5f)
                labelTextView.text = label
                labelTextView.setTypeface(null, Typeface.BOLD)
                labelTextView.setPadding(8, 8, 8, 8)

                val separatorTextView = TextView(this)
                separatorTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.5f)
                separatorTextView.text = ":"
                labelTextView.setTypeface(null, Typeface.BOLD)
                separatorTextView.setPadding(8, 8, 8, 8)

                val valueTextView = TextView(this)
                valueTextView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4f)
                valueTextView.text = value
                labelTextView.setTypeface(null, Typeface.BOLD)
                valueTextView.setPadding(8, 8, 8, 8)

                row.addView(labelTextView)
                row.addView(separatorTextView)
                row.addView(valueTextView)

                detailTable.addView(row)
            }

            // Add rows for each piece of information
            addRow("Name", getData.name)
            addRow("NIM", getData.id)
            addRow("Email", getData.email)
            addRow("Fakultas", getData.faculty)
            addRow("Jurusan", getData.major)
            addRow("Semester", getData.semester.toString())
        }
    }
}