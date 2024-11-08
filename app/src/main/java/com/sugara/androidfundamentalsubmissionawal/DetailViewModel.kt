package com.sugara.androidfundamentalsubmissionawal

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sugara.androidfundamentalsubmissionawal.data.local.entity.EventsEntity
import com.sugara.androidfundamentalsubmissionawal.data.local.repository.EventsRepository
import com.sugara.androidfundamentalsubmissionawal.data.response.Event
import com.sugara.androidfundamentalsubmissionawal.data.response.EventDetailResponse
import com.sugara.androidfundamentalsubmissionawal.data.response.EventsResponse
import com.sugara.androidfundamentalsubmissionawal.data.retrofit.ApiConfig
import com.sugara.androidfundamentalsubmissionawal.ui.EventViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application):ViewModel() {
    fun checkEventInDatabase(id: Int): LiveData<Boolean> {
        val isExist = MutableLiveData<Boolean>()
        mEventsRepository.getEventById(id).observeForever {
            isExist.postValue(it != null)
        }
        return isExist
    }

    private val mEventsRepository: EventsRepository = EventsRepository(application)
    fun insert(event: EventsEntity) {
        mEventsRepository.insert(event)
    }
    fun delete(event: EventsEntity) {
        mEventsRepository.delete(event)
    }

    fun getEventById(id: Int): LiveData<EventsEntity> = mEventsRepository.getEventById(id)
}