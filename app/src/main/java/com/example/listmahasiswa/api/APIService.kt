package com.example.listmahasiswa.api

import com.example.listmahasiswa.model.CreateTaskModel
import com.example.listmahasiswa.model.ResponseCreateTaskModel
import com.example.listmahasiswa.model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Path

interface APIService
{
    @GET("task/")
    fun getData(): Call<ResponseModel>

    //    fun getData(@Body data: DataModel): Call<ResponseModel>
    @POST("task/")
    fun createTask(@Body task: CreateTaskModel): Call<ResponseCreateTaskModel>

    @DELETE("task/{taskId}")
    fun deleteTask(@Path("taskId") taskId: Int?): Call<Void>
}