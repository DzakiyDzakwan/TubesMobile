package com.example.listmahasiswa.model

import java.util.Date

data class TaskModel(val id: Int,
                     val name: String,
                     val mark_as_finished: UByte,
                     val started_at: Date,
                     val deadline_at: Date,
                     val created_at: Date,
                     val updated_at: Date)
