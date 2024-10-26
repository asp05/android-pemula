package com.sugara.androidfundamentalsubmissionawal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sugara.androidfundamentalsubmissionawal.data.response.Event
import com.sugara.androidfundamentalsubmissionawal.data.response.EventDetailResponse
import com.sugara.androidfundamentalsubmissionawal.data.response.EventsResponse
import com.sugara.androidfundamentalsubmissionawal.data.retrofit.ApiConfig
import com.sugara.androidfundamentalsubmissionawal.ui.EventViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel():ViewModel() {
    private val _event = MutableLiveData<Event>()
    val event : LiveData<Event> = _event

    private val _id = MutableLiveData<Int>()
    val id: LiveData<Int> = _id

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    companion object{
        private const val TAG = "DetailViewModel"
    }

    init {
        findEvent()
    }

    fun setId(id: Int) {
        _id.value = id
    }

    private fun findEvent() {
        _isLoading.value = true
        if (_id.value == null) {
            return
        }
        val client = ApiConfig.getApiService().getEventDetail(
            _id.value ?: 0)
        client.enqueue(object : Callback<EventDetailResponse> {
            override fun onResponse(
                call: Call<EventDetailResponse>,
                response: Response<EventDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _event.value = responseBody.event
                    }
                } else {
                    Log.e(DetailViewModel.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<EventDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailViewModel.TAG, "onFailure: ${t.message}")
            }
        })
    }
}