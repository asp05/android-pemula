package com.sugara.androidfundamentalsubmissionawal.helper

import androidx.recyclerview.widget.DiffUtil
import com.sugara.androidfundamentalsubmissionawal.data.local.entity.EventsEntity

class EventsDiffCallback(private val oldNoteList: List<EventsEntity>, private val newNoteList: List<EventsEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].id == newNoteList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.id == newNote.id
    }
}