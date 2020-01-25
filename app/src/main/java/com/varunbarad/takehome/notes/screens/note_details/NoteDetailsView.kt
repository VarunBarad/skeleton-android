package com.varunbarad.takehome.notes.screens.note_details

interface NoteDetailsView {
    fun updateScreen(viewState: NoteDetailsViewState)

    fun showMessage(messageText: String)
}
