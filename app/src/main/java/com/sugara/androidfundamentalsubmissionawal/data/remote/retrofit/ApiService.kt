package com.sugara.androidfundamentalsubmissionawal.data.retrofit
import com.sugara.androidfundamentalsubmissionawal.data.response.EventDetailResponse
import com.sugara.androidfundamentalsubmissionawal.data.response.EventsResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int
    ): Call<EventsResponse>

    @GET("events/{id}")
    fun getEventDetail(
        @Path("id") id: Int
    ): Call<EventDetailResponse>
}