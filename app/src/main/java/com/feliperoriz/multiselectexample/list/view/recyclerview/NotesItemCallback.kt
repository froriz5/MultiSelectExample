package com.feliperoriz.multiselectexample.list.view.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.feliperoriz.multiselectexample.repository.db.model.NoteEntity

class NotesItemCallback: DiffUtil.ItemCallback<NoteEntity>() {
    override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem == newItem
    }
}