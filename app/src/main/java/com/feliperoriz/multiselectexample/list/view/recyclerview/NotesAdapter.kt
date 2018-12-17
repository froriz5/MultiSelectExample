package com.feliperoriz.multiselectexample.list.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter

import com.feliperoriz.multiselectexample.R
import com.feliperoriz.multiselectexample.repository.db.model.NoteEntity

class NotesAdapter(private val inflator: LayoutInflater,
                   itemCallback: NotesItemCallback): PagedListAdapter<NoteEntity, NoteViewHolder>(itemCallback) {

    var onClickNote: (NoteEntity) -> Unit = {}

    var isBulkModeEnabled: Boolean = false
        set(value) {
            field = value
            notifyDataSetChanged()

            if (field) {
                onBulkModeStarted()
            } else {
                onBulkModeEnded()
            }
        }
    var onBulkItemSelected: (NoteEntity, Boolean) -> Unit = { note, isSelected -> }
    var onBulkModeStarted: () -> Unit = {}
    var onBulkModeEnded: () -> Unit = {}

    private val onLongClickNote: (NoteEntity, Boolean) -> Unit = { note, isSelected ->
        if (!isBulkModeEnabled) {
            isBulkModeEnabled = true
        }
        onBulkItemSelected(note, isSelected)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = inflator.inflate(R.layout.note_row_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        note?.let { holder.bindNote(it, isBulkModeEnabled, onClickNote, onLongClickNote, onBulkItemSelected) }
    }
}