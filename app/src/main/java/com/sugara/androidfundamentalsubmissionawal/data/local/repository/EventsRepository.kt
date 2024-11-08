package com.sugara.androidfundamentalsubmissionawal.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sugara.androidfundamentalsubmissionawal.data.local.entity.EventsEntity
import com.sugara.androidfundamentalsubmissionawal.data.local.room.EventsDao
import com.sugara.androidfundamentalsubmissionawal.data.local.room.EventsDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EventsRepository(application: Application) {
    private val mEventsDao: EventsDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = EventsDatabase.getDatabase(application)
        mEventsDao = db.eventsDao()
    }
    fun getAllEvents(): LiveData<List<EventsEntity>> = mEventsDao.getAllEvents()
    fun insert(events: EventsEntity) {
        executorService.execute { mEventsDao.insert(events) }
    }
    fun delete(events: EventsEntity) {
        executorService.execute { mEventsDao.delete(events) }
    }

    fun getEventById(id: Int): LiveData<EventsEntity> = mEventsDao.getEventById(id)

}