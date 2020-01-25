package com.varunbarad.takehome.notes.screens.list_notes.notes_adapter

import androidx.recyclerview.widget.RecyclerView
import com.varunbarad.takehome.notes.databinding.ListItemNoteBinding
import com.varunbarad.takehome.notes.model.UiNote

class NoteViewHolder(
    private val viewBinding: ListItemNoteBinding,
    private val noteClickListener: (UiNote) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {
    fun bind(note: UiNote) {
        this.viewBinding.textViewTitle.text = note.title
        this.viewBinding.textViewContent.text = note.content

        this.viewBinding.containerNote.setOnClickListener { this.noteClickListener(note) }

        this.viewBinding.executePendingBindings()
    }
}
