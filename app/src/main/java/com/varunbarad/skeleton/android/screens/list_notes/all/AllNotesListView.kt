package com.varunbarad.skeleton.android.screens.list_notes.all

import com.varunbarad.skeleton.android.model.UiNote
import io.reactivex.Observable

interface AllNotesListView {
    fun onNoteClick(): Observable<UiNote>

    fun updateScreen(viewState: AllNotesListViewState)
    fun showMessage(messageText: String)

    fun openNoteDetailsScreen(noteId: Long)
}
