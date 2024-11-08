package com.sugara.androidfundamentalsubmissionawal.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import com.sugara.androidfundamentalsubmissionawal.data.local.entity.EventsEntity
import com.sugara.androidfundamentalsubmissionawal.data.local.repository.EventsRepository
import androidx.lifecycle.ViewModel

class FavoriteViewModel (application: Application) : ViewModel() {
    private val mEventsRepository: EventsRepository = EventsRepository(application)
    fun getAllEvents(): LiveData<List<EventsEntity>> = mEventsRepository.getAllEvents()
}