package com.feliperoriz.multiselectexample.list.view.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.feliperoriz.multiselectexample.repository.db.model.NoteEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.note_row_item.*

class NoteViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    inline fun bindNote(note: NoteEntity, isBulkModeOn: Boolean,
                        crossinline onClickNote: (NoteEntity) -> Unit,
                        crossinline onLongClickNote: (NoteEntity, Boolean) -> Unit,
                        crossinline onNoteBulkSelected: (NoteEntity, Boolean) -> Unit) {

        title_view.text = note.title

        val body = if (note.body.isNotEmpty()) note.body else "No description"
        description_view.text = body

        if (isBulkModeOn) {
            bulk_checkbox.visibility = View.VISIBLE
            title_view.alpha = 0.4f
            description_view.alpha = 0.4f

            root_view.setOnClickListener { onNoteBulkSelected(note, !bulk_checkbox.isChecked) }
        } else {
            bulk_checkbox.visibility = View.GONE

            title_view.alpha = 1f
            description_view.alpha = 1f
            root_view.setOnClickListener { onClickNote(note) }
        }

        root_view.setOnLongClickListener {
            onLongClickNote(note, !bulk_checkbox.isChecked)
            true
        }
    }
}