package com.example.calefit.network

import com.example.calefit.data.dto.CalendarDateListDTO
import retrofit2.http.GET

interface RemoteApi {

    @GET("/date")
    suspend fun getDateList(): CalendarDateListDTO
}