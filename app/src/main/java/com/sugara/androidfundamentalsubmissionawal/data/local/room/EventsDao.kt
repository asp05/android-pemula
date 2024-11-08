package com.sugara.androidfundamentalsubmissionawal.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sugara.androidfundamentalsubmissionawal.data.local.entity.EventsEntity

@Dao
interface EventsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: EventsEntity)
    @Delete
    fun delete(event: EventsEntity)
    @Query("SELECT * from events ORDER BY id ASC")
    fun getAllEvents(): LiveData<List<EventsEntity>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getEventById(id: Int): LiveData<EventsEntity>
}