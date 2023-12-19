package com.example.listmahasiswa.api

import com.example.listmahasiswa.model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface APIService
{
    @GET("http://192.168.1.3:3333/api/v1/task/")
    fun getData(): Call<ResponseModel>
//    fun getData(@Body data: DataModel): Call<ResponseModel>
}