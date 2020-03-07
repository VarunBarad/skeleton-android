package com.varunbarad.skeleton.android.screens.list_notes.notes_adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.varunbarad.skeleton.android.databinding.ListItemNoteBinding
import com.varunbarad.skeleton.android.model.UiNote

class NoteViewHolder(
    private val viewBinding: ListItemNoteBinding,
    private val noteClickListener: (UiNote) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {
    fun bind(note: UiNote) {
        this.viewBinding.textViewTitle.text = note.title
        this.viewBinding.textViewContent.text = note.content

        if (note.isBookmarked) {
            this.viewBinding.imageViewBookmarkIcon.visibility = View.VISIBLE
        } else {
            this.viewBinding.imageViewBookmarkIcon.visibility = View.GONE
        }

        this.viewBinding.containerNote.setOnClickListener { this.noteClickListener(note) }
    }
}
