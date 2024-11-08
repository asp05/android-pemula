package com.sugara.androidfundamentalsubmissionawal.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "events")
@Parcelize
data class EventsEntity (
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int? = 0,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "imageLogo")
    var imageLogo: String? = null,

) : Parcelable