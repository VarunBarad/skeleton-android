package com.varunbarad.skeleton.android.screens.note_details

interface NoteDetailsView {
    fun updateScreen(viewState: NoteDetailsViewState)

    fun showMessage(messageText: String)
}
