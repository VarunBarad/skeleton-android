package com.varunbarad.skeleton.android.screens.list_notes

import com.varunbarad.skeleton.android.model.UiNote
import com.varunbarad.skeleton.android.util.Event
import io.reactivex.Observable

interface ListNotesView {
    fun onButtonCreateNoteClick(): Observable<Event>
    fun onNoteClick(): Observable<UiNote>

    fun updateScreen(viewState: ListNotesViewState)
    fun showMessage(messageText: String)

    fun openCreateNoteScreen()
    fun openNoteDetailsScreen(noteId: Long)
}
