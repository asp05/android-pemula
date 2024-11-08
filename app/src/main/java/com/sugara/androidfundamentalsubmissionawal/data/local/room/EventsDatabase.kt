package com.sugara.androidfundamentalsubmissionawal.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sugara.androidfundamentalsubmissionawal.data.local.entity.EventsEntity

@Database(entities = [EventsEntity::class], version = 1, exportSchema = false)
abstract class EventsDatabase: RoomDatabase() {
    abstract fun eventsDao(): EventsDao
    companion object {
        @Volatile
        private var INSTANCE: EventsDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): EventsDatabase {
            if (INSTANCE == null) {
                synchronized(EventsDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        EventsDatabase::class.java, "events_database")
                        .build()
                }
            }
            return INSTANCE as EventsDatabase
        }
    }
}